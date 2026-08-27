package org.edufood.edufood.service.service_interface;

import org.edufood.edufood.dto.request.UserRegisterRequest;
import org.edufood.edufood.entities.User;

public interface UserService {

    /**
     * Регистрирует нового пользователя: хеширует пароль (BCrypt),
     * назначает роль ROLE_USER и сохраняет в БД.
     *
     * @throws IllegalArgumentException если email уже занят
     */
    User register(UserRegisterRequest request);

    boolean existsByEmail(String email);
}
