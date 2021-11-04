package com.example.demo.src.company.model;

import com.example.demo.src.employment.model.Employment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetCompanyRes {
    private String companyName;
    private List<Employment> employmentList;
    private List<String> tags;
    private CompanyImg imageUrls;
    private String companyIntroduce;
}
