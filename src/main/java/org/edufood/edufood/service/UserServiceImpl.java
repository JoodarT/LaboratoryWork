package org.edufood.edufood.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.edufood.edufood.dto.request.UserRegisterRequest;
import org.edufood.edufood.entities.Role;
import org.edufood.edufood.entities.User;
import org.edufood.edufood.repository.RoleRepository;
import org.edufood.edufood.repository.UserRepository;
import org.edufood.edufood.service.service_interface.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final String DEFAULT_ROLE = "ROLE_USER";

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public User register(UserRegisterRequest request) {
        String email = request.getEmail().trim().toLowerCase();

        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Пользователь с email " + email + " уже зарегистрирован");
        }

        Role userRole = roleRepository.findByName(DEFAULT_ROLE)
                .orElseThrow(() -> new IllegalStateException("Роль " + DEFAULT_ROLE + " не найдена в БД"));

        User user = User.builder()
                .email(email)
                .password(passwordEncoder.encode(request.getPassword()))
                .fullName(request.getFullName().trim())
                .build();
        user.addRole(userRole);

        User saved = userRepository.save(user);
        log.info("Зарегистрирован новый пользователь: {} (id={})", saved.getEmail(), saved.getId());
        return saved;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email.trim().toLowerCase());
    }
}
