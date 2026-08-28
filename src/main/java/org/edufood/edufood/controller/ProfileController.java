package org.edufood.edufood.controller;

import lombok.RequiredArgsConstructor;
import org.edufood.edufood.service.service_interface.OrderService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final OrderService orderService;

    @GetMapping
    public String profile() {
        return "redirect:/profile/orders";
    }

    @GetMapping("/orders")
    public String orders(Authentication authentication, Model model) {
        // Запрос всегда ограничен email текущего пользователя — чужие заказы недоступны
        model.addAttribute("orders", orderService.getUserOrders(authentication.getName()));
        return "profile/orders";
    }
}
