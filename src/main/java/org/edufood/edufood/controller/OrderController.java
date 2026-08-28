package org.edufood.edufood.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.edufood.edufood.service.service_interface.OrderService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/checkout")
    public String checkout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication,
            RedirectAttributes redirectAttributes
    ) {
        orderService.createOrderFromCart(request, response, authentication.getName());
        redirectAttributes.addFlashAttribute("orderCreated", true);
        return "redirect:/profile/orders";
    }
}