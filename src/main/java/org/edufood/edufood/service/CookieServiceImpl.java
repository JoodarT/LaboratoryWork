package org.edufood.edufood.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.edufood.edufood.dto.CartCookieDto;
import org.edufood.edufood.dto.CartDto;
import org.edufood.edufood.dto.CartItemDto;
import org.edufood.edufood.entities.Dish;
import org.edufood.edufood.repository.DishRepository;
import org.edufood.edufood.service.service_interface.CookieService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class CookieServiceImpl implements CookieService {

    public static final String CART_COOKIE_NAME = "EDUFOOD_CART";
    private static final int COOKIE_MAX_AGE = 7 * 24 * 60 * 60;

    private final ObjectMapper objectMapper;
    private final DishRepository dishRepository;

    @Override
    public CartDto getCart(HttpServletRequest request) {
        CartCookieDto cookieData = readCartCookie(request);

        if (cookieData.getItems().isEmpty()) {
            return new CartDto();
        }

        List<CartItemDto> cartItems = new ArrayList<>();
        BigDecimal totalPrice = BigDecimal.ZERO;
        int totalQuantity = 0;

        for (CartCookieDto.CookieItem item : cookieData.getItems()) {
            Optional<Dish> dishOpt = dishRepository.findById(item.getDishId());
            if (dishOpt.isEmpty()) {
                continue;
            }

            Dish dish = dishOpt.get();
            int qty = item.getQuantity();
            BigDecimal price = dish.getPrice();
            BigDecimal subtotal = price.multiply(BigDecimal.valueOf(qty));

            totalPrice = totalPrice.add(subtotal);
            totalQuantity += qty;

            cartItems.add(CartItemDto.builder()
                    .dishId(dish.getId())
                    .dishName(dish.getName())
                    .restaurantId(dish.getRestaurant().getId())
                    .restaurantName(dish.getRestaurant().getName())
                    .price(price)
                    .quantity(qty)
                    .imageUrl(dish.getImageUrl())
                    .build());
        }

        return CartDto.builder()
                .items(cartItems)
                .totalPrice(totalPrice)
                .totalQuantity(totalQuantity)
                .build();
    }

    @Override
    public void addToCart(HttpServletRequest request, HttpServletResponse response, Long dishId, int quantity) {
        Dish dishToAdd = dishRepository.findById(dishId)
                .orElseThrow(() -> new IllegalArgumentException("Блюдо с ID " + dishId + " не найдено"));

        CartCookieDto cookieData = readCartCookie(request);
        String currentUserId = resolveUserId(request);
        cookieData.setUserId(currentUserId);

        if (!cookieData.getItems().isEmpty()) {
            Long firstDishId = cookieData.getItems().getFirst().getDishId();
            Optional<Dish> firstDishOpt = dishRepository.findById(firstDishId);

            if (firstDishOpt.isPresent()) {
                Long currentRestaurantId = firstDishOpt.get().getRestaurant().getId();
                Long newRestaurantId = dishToAdd.getRestaurant().getId();

                if (!currentRestaurantId.equals(newRestaurantId)) {
                    log.warn("Пользователь {} сменил ресторан с {} на {}. Корзина очищена и начата заново.",
                            currentUserId, currentRestaurantId, newRestaurantId);
                    cookieData.getItems().clear();
                }
            }
        }

        Optional<CartCookieDto.CookieItem> existingItem = cookieData.getItems().stream()
                .filter(item -> item.getDishId().equals(dishId))
                .findFirst();

        if (existingItem.isPresent()) {
            CartCookieDto.CookieItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);
        } else {
            cookieData.getItems().add(new CartCookieDto.CookieItem(dishId, quantity));
        }

        saveCartCookie(response, cookieData);
        log.info("Блюдо '{}' (ID: {}, x{}) добавлено в корзину пользователя {}",
                dishToAdd.getName(), dishId, quantity, currentUserId);
    }

    @Override
    public void updateQuantity(HttpServletRequest request, HttpServletResponse response, Long dishId, int quantity) {
        CartCookieDto cookieData = readCartCookie(request);

        if (quantity <= 0) {
            cookieData.getItems().removeIf(item -> item.getDishId().equals(dishId));
        } else {
            cookieData.getItems().stream()
                    .filter(item -> item.getDishId().equals(dishId))
                    .findFirst()
                    .ifPresent(item -> item.setQuantity(quantity));
        }

        saveCartCookie(response, cookieData);
    }

    @Override
    public void removeFromCart(HttpServletRequest request, HttpServletResponse response, Long dishId) {
        CartCookieDto cookieData = readCartCookie(request);
        cookieData.getItems().removeIf(item -> item.getDishId().equals(dishId));
        saveCartCookie(response, cookieData);
        log.info("Блюдо ID {} удалено из корзины", dishId);
    }

    @Override
    public void clearCartCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie(CART_COOKIE_NAME, "");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        log.info("Кука корзины успешно очищена");
    }

    @Override
    public void rebindCartToUser(HttpServletRequest request, HttpServletResponse response, String userEmail) {
        CartCookieDto cookieData = readCartCookie(request);
        cookieData.setUserId(userEmail);
        saveCartCookie(response, cookieData);
        log.info("Корзина успешно перепривязана к пользователю: {}", userEmail);
    }

    private CartCookieDto readCartCookie(HttpServletRequest request) {
        if (request.getCookies() == null) {
            return new CartCookieDto(resolveUserId(request), new ArrayList<>());
        }

        return Arrays.stream(request.getCookies())
                .filter(c -> CART_COOKIE_NAME.equals(c.getName()))
                .findFirst()
                .map(this::deserializeCookie)
                .orElseGet(() -> new CartCookieDto(resolveUserId(request), new ArrayList<>()));
    }

    private void saveCartCookie(HttpServletResponse response, CartCookieDto cookieData) {
        try {
            String json = objectMapper.writeValueAsString(cookieData);
            String encoded = URLEncoder.encode(json, StandardCharsets.UTF_8);

            Cookie cookie = new Cookie(CART_COOKIE_NAME, encoded);
            cookie.setPath("/");
            cookie.setMaxAge(COOKIE_MAX_AGE);
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
        } catch (Exception e) {
            log.error("Ошибка сохранения корзины в Cookie", e);
        }
    }

    private CartCookieDto deserializeCookie(Cookie cookie) {
        try {
            String json = URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8);
            return objectMapper.readValue(json, CartCookieDto.class);
        } catch (Exception e) {
            log.warn("Не удалось прочитать корзину из Cookie, создается новая", e);
            return new CartCookieDto(getCurrentAuthEmail(), new ArrayList<>());
        }
    }

    private String resolveUserId(HttpServletRequest request) {
        String authEmail = getCurrentAuthEmail();
        if (authEmail != null) {
            return authEmail;
        }
        return "guest-" + request.getSession(true).getId();
    }

    private String getCurrentAuthEmail() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
            return auth.getName();
        }
        return null;
    }
}