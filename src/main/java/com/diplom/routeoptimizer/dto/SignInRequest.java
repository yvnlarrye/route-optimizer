package com.diplom.routeoptimizer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignInRequest {
    @Size(min = 5, max = 255, message = "Имя пользователя должно быть от 5 до 255 символов")
    @NotBlank(message = "Адрес электронной почты не может быть пустыми")
    private String username;

    @Size(min = 8, message = "Длина пароля должна быть не менее 8 символов")
    @Size(max = 255, message = "Длина пароля должна быть не более 255 символов")
    private String password;
}
