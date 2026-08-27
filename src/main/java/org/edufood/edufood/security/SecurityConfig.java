package org.edufood.edufood.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Отключаем для корректной работы форм добавления в корзину
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/restaurants/**", "/cart/**", "/h2-console/**", "/css/**", "/js/**").permitAll()
                        .anyRequest().permitAll() // Разрешаем доступ, пока Разработчик 2 настраивает авторизацию
                )
                .headers(headers -> headers.frameOptions(frame -> frame.disable())); // Для доступа к H2-Console

        return http.build();
    }
}