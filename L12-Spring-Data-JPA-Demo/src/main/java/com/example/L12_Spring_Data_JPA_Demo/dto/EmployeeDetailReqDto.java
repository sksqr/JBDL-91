package com.example.L12_Spring_Data_JPA_Demo.dto;


import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDetailReqDto {
    private String name;
    private String email;
    private String line1;
    private String line2;
    private String city;
    private Long branchId;
}
