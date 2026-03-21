package com.example.L13_minor_project_01.dto;

import com.example.L13_minor_project_01.entity.UserRole;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CompanyResponseDto {

    private Long id;
    private String name;
    private String number;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private PrimaryUserResponseDto primaryUser;

    @Getter
    @Builder
    public static class PrimaryUserResponseDto {
        private Long id;
        private String name;
        private String email;
        private UserRole role;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }
}
