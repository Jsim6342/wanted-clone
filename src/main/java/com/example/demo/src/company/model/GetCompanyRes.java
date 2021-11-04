package com.example.demo.src.company.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCompanyRes {
    private String CompanyName;
    private List<EmploymentDTO> EmploymentList;
    private List<String> tags;
    private List<String> imageUrl;
    private String companyIntroduce;
}
