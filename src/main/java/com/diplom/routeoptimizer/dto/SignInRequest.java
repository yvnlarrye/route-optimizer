package com.diplom.routeoptimizer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignInRequest {
    @NotBlank(message = "Имя пользователя не может быть пустыми")
    private String username;

    @NotBlank(message = "Пароль не может быть пустыми")
    private String password;
}
