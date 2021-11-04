package com.example.demo.src.company.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyImg {
    private Long companyImgIdx;
    private Long companyIdx;
    private String companyImg1;
    private String companyImg2;
    private String companyImg3;
    private String companyImg4;
    private String companyImg5;

    public CompanyImg(String companyImg1, String companyImg2, String companyImg3, String companyImg4, String companyImg5) {
        this.companyImg1 = companyImg1;
        this.companyImg2 = companyImg2;
        this.companyImg3 = companyImg3;
        this.companyImg4 = companyImg4;
        this.companyImg5 = companyImg5;
    }
}
