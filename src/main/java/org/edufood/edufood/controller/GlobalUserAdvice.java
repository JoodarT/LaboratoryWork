package org.edufood.edufood.controller;

import lombok.RequiredArgsConstructor;
import org.edufood.edufood.entities.User;
import org.edufood.edufood.repository.UserRepository;
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
@RequiredArgsConstructor
public class GlobalUserAdvice {

    private final UserRepository userRepository;

    @ModelAttribute("currentUser")
    public User currentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal())) {
            return null;
        }
        return userRepository.findByEmail(auth.getName()).orElse(null);
    }
}
