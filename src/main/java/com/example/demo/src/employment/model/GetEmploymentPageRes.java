package com.example.demo.src.employment.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetEmploymentPageRes {
    private Long employmentIdx;
    private String companyImg;
    private String empTitle;
    private String companyName;
    private String companyLocation;
    private Long reward;
}
