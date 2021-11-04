package com.example.demo.src.company.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostCompanyReq {
    private String companyName;
    private String companyNation;
    private String companyLocation;
    private String companyAddress;
    private String registrationNum;
    private String sales;
    private String industryGroup;
    private Long companySize;
    private String introduce;
    private String establishment;
    private String companyEmail;
    private String companyPhone;
    private String companyUrl;
    private String response;
}
