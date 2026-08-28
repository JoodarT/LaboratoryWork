package org.edufood.edufood.service.service_interface;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.edufood.edufood.dto.CartDto;

public interface CookieService {

    CartDto getCart(HttpServletRequest request);

    void addToCart(HttpServletRequest request, HttpServletResponse response, Long dishId, int quantity);

    void updateQuantity(HttpServletRequest request, HttpServletResponse response, Long dishId, int quantity);

    void removeFromCart(HttpServletRequest request, HttpServletResponse response, Long dishId);

    void clearCartCookie(HttpServletResponse response);

    void rebindCartToUser(HttpServletRequest request, HttpServletResponse response, String userEmail);
}