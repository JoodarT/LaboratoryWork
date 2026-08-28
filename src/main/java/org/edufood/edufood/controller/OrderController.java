package org.edufood.edufood.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.edufood.edufood.entities.Order;
import org.edufood.edufood.service.service_interface.OrderService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public String checkout(Authentication authentication,
                           HttpServletRequest request,
                           HttpServletResponse response,
                           RedirectAttributes redirectAttributes) {
        try {
            Order order = orderService.checkout(authentication.getName(), request, response);
            return "redirect:/orders/" + order.getId();
        } catch (IllegalStateException e) {
            log.warn("Не удалось оформить заказ пользователя {}: {}", authentication.getName(), e.getMessage());
            redirectAttributes.addFlashAttribute("cartError", e.getMessage());
            return "redirect:/cart";
        }
    }

    @GetMapping("/{id}")
    public String viewOrder(@PathVariable Long id, Authentication authentication, Model model) {
        Order order = orderService.getForUser(id, authentication.getName());
        model.addAttribute("order", order);
        return "orders/confirmation";
    }
}
