package org.edufood.edufood.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.edufood.edufood.dto.CartDto;
import org.edufood.edufood.service.service_interface.CookieService;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalCartAdvice {

    private final CookieService cookieService;

    @ModelAttribute("cart")
    public CartDto populateCart(HttpServletRequest request) {
        return cookieService.getCart(request);
    }
}