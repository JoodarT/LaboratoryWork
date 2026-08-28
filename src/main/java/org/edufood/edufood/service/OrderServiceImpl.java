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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final DishRepository dishRepository;
    private final CookieService cookieService;

    @Override
    @Transactional
    public Order createOrderFromCart(HttpServletRequest request, HttpServletResponse response, String userEmail) {
        CartDto cart = cookieService.getCart(request);
        if (cart.getItems() == null || cart.getItems().isEmpty()) {
            throw new IllegalStateException("Невозможно оформить заказ: корзина пуста");
        }

        User user = userRepository.findByEmail(userEmail.trim().toLowerCase())
                .orElseThrow(() -> new IllegalArgumentException("Пользователь с email " + userEmail + " не найден"));

        Order order = Order.builder()
                .user(user)
                .totalPrice(BigDecimal.ZERO)
                .build();

        BigDecimal calculatedTotal = BigDecimal.ZERO;

        for (CartItemDto cartItem : cart.getItems()) {
            Dish dish = dishRepository.findById(cartItem.getDishId())
                    .orElseThrow(() -> new IllegalArgumentException("Блюдо с ID " + cartItem.getDishId() + " не найдено"));

            OrderItem orderItem = OrderItem.builder()
                    .dish(dish)
                    .quantity(cartItem.getQuantity())
                    .priceAtOrder(dish.getPrice()) // Фиксируем актуальную цену из базы данных
                    .build();

            order.addItem(orderItem);

            BigDecimal itemTotal = dish.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));
            calculatedTotal = calculatedTotal.add(itemTotal);
        }

        order.setTotalPrice(calculatedTotal);
        Order savedOrder = orderRepository.save(order);

        cookieService.clearCartCookie(response);

        log.info("Заказ #{} успешно оформлен пользователем {} на сумму {} сом",
                savedOrder.getId(), user.getEmail(), savedOrder.getTotalPrice());

        return savedOrder;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> getUserOrders(String userEmail) {
        log.info("Запрос истории заказов для пользователя: {}", userEmail);
        return orderRepository.findAllByUserEmailWithItems(userEmail.trim().toLowerCase());
    }
}