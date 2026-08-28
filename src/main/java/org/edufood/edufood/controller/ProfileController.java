package org.edufood.edufood.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.edufood.edufood.entities.Order;
import org.edufood.edufood.service.service_interface.OrderService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final OrderService orderService;

    @GetMapping("/orders")
    public String viewOrders(Authentication authentication, Model model) {
        List<Order> orders = orderService.getUserOrders(authentication.getName());
        model.addAttribute("orders", orders);
        return "profile/orders";
    }
}