package com.sparta.currency_user.dto;

import com.sparta.currency_user.entity.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserRequestDto {

    @NotBlank(message = "이름은 필수 값입니다")
    private String name;
    @NotBlank(message = "이메일은 필수 값입니다")
    private String email;

    public User toEntity() {
        return new User(
                this.name,
                this.email
        );
    }
}
