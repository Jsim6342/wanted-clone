package com.example.demo.src.company.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmploymentDTO {
    private String empTitle;
    private Long reward;
    private String empDeadline;
    private Long bookmark;
}
