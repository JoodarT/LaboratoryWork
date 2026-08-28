package org.edufood.edufood.service.service_interface;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.edufood.edufood.entities.Order;

import java.util.List;

public interface OrderService {

    Order createOrderFromCart(HttpServletRequest request, HttpServletResponse response, String userEmail);

    List<Order> getUserOrders(String userEmail);
}