package org.edufood.edufood.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterRequest {

    @NotBlank(message = "Имя не может быть пустым")
    @Size(min = 2, max = 100, message = "Имя должно содержать от 2 до 100 символов")
    private String fullName;

    @NotBlank(message = "Email обязателен для заполнения")
    @Email(message = "Некорректный формат email адреса")
    private String email;

    @NotBlank(message = "Пароль не может быть пустым")
    @Size(min = 6, max = 50, message = "Пароль должен содержать от 6 до 50 символов")
    private String password;

    @NotBlank(message = "Повторите пароль")
    private String confirmPassword;

}