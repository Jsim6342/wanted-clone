package com.example.demo.src.company.model.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatchCompanyReq {

    private String companyName;
    private String companyNation;
    private String companyLocation;
    private String companyAddress;
    private String sales;
    private String industryGroup;
    private Long companySize;
    private String introduce;
    private String companyEmail;
    private String companyPhone;
    private String companyUrl;

}
