package com.example.L13_minor_project_01.dto;

import com.example.L13_minor_project_01.entity.UserRole;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class SellerResponseDto {

    private Long id;
    private String name;
    private String email;
    private UserRole role;
    private Long companyId;
    private String companyName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
