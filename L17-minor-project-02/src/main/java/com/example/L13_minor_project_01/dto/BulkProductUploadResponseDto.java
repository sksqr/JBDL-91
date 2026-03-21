package com.example.L13_minor_project_01.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class BulkProductUploadResponseDto {

    private int totalRows;
    private int createdCount;
    private int failedCount;
    private List<String> errors;
}
