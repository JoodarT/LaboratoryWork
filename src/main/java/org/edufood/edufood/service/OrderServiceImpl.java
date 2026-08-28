package org.edufood.edufood.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.edufood.edufood.dto.CartDto;
import org.edufood.edufood.dto.CartItemDto;
import org.edufood.edufood.entities.Dish;
import org.edufood.edufood.entities.Order;
import org.edufood.edufood.entities.OrderItem;
import org.edufood.edufood.entities.User;
import org.edufood.edufood.repository.DishRepository;
import org.edufood.edufood.repository.OrderRepository;
import org.edufood.edufood.repository.UserRepository;
import org.edufood.edufood.service.service_interface.CookieService;
import org.edufood.edufood.service.service_interface.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private static final int MAX_QUANTITY_PER_ITEM = 50;

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final DishRepository dishRepository;
    private final CookieService cookieService;

    @Override
    @Transactional
    public Order checkout(String userEmail, HttpServletRequest request, HttpServletResponse response) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalStateException("Пользователь не найден: " + userEmail));

        CartDto cart = cookieService.getCart(request);
        if (cart.getItems() == null || cart.getItems().isEmpty()) {
            throw new IllegalStateException("Корзина пуста — оформить заказ нельзя");
        }

        Order order = new Order();
        order.setUser(user);

        BigDecimal total = BigDecimal.ZERO;

        for (CartItemDto cartItem : cart.getItems()) {
            Dish dish = dishRepository.findById(cartItem.getDishId())
                    .orElseThrow(() -> new IllegalStateException(
                            "Блюдо больше не доступно (ID " + cartItem.getDishId() + ")"));

            // Один заказ — один ресторан (гарантируется логикой корзины, проверяем ещё раз)
            if (order.getRestaurant() == null) {
                order.setRestaurant(dish.getRestaurant());
            } else if (!order.getRestaurant().getId().equals(dish.getRestaurant().getId())) {
                throw new IllegalStateException("Корзина содержит блюда из разных ресторанов");
            }

            int quantity = Math.min(Math.max(cartItem.getQuantity() == null ? 0 : cartItem.getQuantity(), 1),
                    MAX_QUANTITY_PER_ITEM);

            BigDecimal priceAtOrder = dish.getPrice(); // источник истины по цене — БД, не Cookie

            OrderItem orderItem = new OrderItem();
            orderItem.setDish(dish);
            orderItem.setQuantity(quantity);
            orderItem.setPriceAtOrder(priceAtOrder);
            order.addItem(orderItem);

            total = total.add(priceAtOrder.multiply(BigDecimal.valueOf(quantity)));
        }

        order.setTotalPrice(total);

        Order saved = orderRepository.save(order); // каскадом сохраняются и позиции
        cookieService.clearCartCookie(response);

        log.info("Пользователь {} оформил заказ #{} (ресторан «{}», позиций: {}, сумма: {})",
                userEmail, saved.getId(), saved.getRestaurant().getName(),
                saved.getItems().size(), total);

        return saved;
    }

    @Override
    @Transactional(readOnly = true)
    public Order getForUser(Long orderId, String userEmail) {
        return orderRepository.findByIdAndUserEmail(orderId, userEmail)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Заказ не найден"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> getUserOrders(String userEmail) {
        return orderRepository.findByUserEmailOrderByCreatedAtDesc(userEmail);
    }
}
