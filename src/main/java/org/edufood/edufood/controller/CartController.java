package org.edufood.edufood.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.edufood.edufood.dto.CartDto;
import org.edufood.edufood.service.service_interface.CookieService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CookieService cookieService;

    @GetMapping
    public String viewCart(HttpServletRequest request, Model model) {
        CartDto cart = cookieService.getCart(request);
        model.addAttribute("cart", cart);
        return "cart/cart";
    }

    @PostMapping("/add")
    public String addToCart(
            @RequestParam("dishId") Long dishId,
            @RequestParam(name = "quantity", defaultValue = "1") int quantity,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        cookieService.addToCart(request, response, dishId, quantity);

        String referer = request.getHeader("Referer");
        return referer != null && !referer.isBlank() ? "redirect:" + referer : "redirect:/restaurants";
    }

    @PostMapping("/update")
    public String updateQuantity(
            @RequestParam("dishId") Long dishId,
            @RequestParam("quantity") int quantity,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        cookieService.updateQuantity(request, response, dishId, quantity);
        return "redirect:/cart";
    }

    @PostMapping("/remove")
    public String removeFromCart(
            @RequestParam("dishId") Long dishId,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        cookieService.removeFromCart(request, response, dishId);
        return "redirect:/cart";
    }

    @PostMapping("/clear")
    public String clearCart(HttpServletResponse response) {
        cookieService.clearCartCookie(response);
        return "redirect:/cart";
    }
}