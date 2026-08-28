package org.edufood.edufood.service.service_interface;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.edufood.edufood.entities.Order;

import java.util.List;

public interface OrderService {

    /**
     * Оформляет заказ из корзины в Cookie: читает состав, фиксирует цены из БД,
     * привязывает к пользователю, сохраняет в одной транзакции и очищает Cookie корзины.
     *
     * @throws IllegalStateException если корзина пуста или содержит некорректные данные
     */
    Order checkout(String userEmail, HttpServletRequest request, HttpServletResponse response);

    /**
     * Возвращает заказ по id, только если он принадлежит пользователю (защита от IDOR).
     *
     * @throws org.springframework.web.server.ResponseStatusException 404, если чужой или не найден
     */
    Order getForUser(Long orderId, String userEmail);

    /**
     * История заказов пользователя, отсортированная по дате создания (новые сверху).
     */
    List<Order> getUserOrders(String userEmail);
}
