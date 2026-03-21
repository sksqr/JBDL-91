package com.example.L13_minor_project_01.dto;

import com.example.L13_minor_project_01.entity.UserRole;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCompanyRequestDto {

    @NotBlank
    private String name;

    @NotBlank
    private String number;

    private Boolean isActive;

    @Valid
    @NotNull
    private PrimaryUserDto primaryUser;

    @Getter
    @Setter
    public static class PrimaryUserDto {
        @NotBlank
        private String name;

        @NotBlank
        @Email
        private String email;

        @NotBlank
        private String password;

        private UserRole role;
    }
}
