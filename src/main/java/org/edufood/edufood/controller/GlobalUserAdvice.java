package org.edufood.edufood.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * Прокидывает во все шаблоны данные о текущем аутентифицированном пользователе
 * для блока профиля/входа в layout.ftlh (обработка Spring Security).
 * Для гостя возвращает null — в навбаре показываются ссылки «Войти / Регистрация».
 */
@ControllerAdvice
public class GlobalUserAdvice {

    @ModelAttribute("currentUsername")
    public String currentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal())) {
            return null;
        }
        return auth.getName();
    }
}
