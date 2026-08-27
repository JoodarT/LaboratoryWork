package org.edufood.edufood.service.service_interface;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.edufood.edufood.dto.CartDto;

public interface CookieService {

    /**
     * Получить полную корзину для отображения на UI.
     * Цены, названия и рестораны загружаются из БД на основе dishId из Cookie.
     */
    CartDto getCart(HttpServletRequest request);

    /**
     * Добавить блюдо в корзину.
     * Реализует правило «Один заказ — один ресторан»:
     * если добавляется блюдо из другого ресторана, корзина очищается для нового заведения.
     */
    void addToCart(HttpServletRequest request, HttpServletResponse response, Long dishId, int quantity);

    /**
     * Изменить количество блюда в корзине.
     */
    void updateQuantity(HttpServletRequest request, HttpServletResponse response, Long dishId, int quantity);

    /**
     * Удалить конкретное блюдо из корзины.
     */
    void removeFromCart(HttpServletRequest request, HttpServletResponse response, Long dishId);

    /**
     * Полностью очистить куку корзины.
     */
    void clearCartCookie(HttpServletResponse response);

    /**
     * Перепривязать гостевую корзину к авторизованному пользователю.
     * Вызывается при успешном входе/регистрации (для Разработчика 2).
     */
    void rebindCartToUser(HttpServletRequest request, HttpServletResponse response, String userEmail);
}