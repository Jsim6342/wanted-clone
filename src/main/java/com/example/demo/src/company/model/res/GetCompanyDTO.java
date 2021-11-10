package com.example.demo.src.company.model.res;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class GetCompanyDTO {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ResponseDTO {
        private String companyName;
        private String companyIntroduce;
        private List<Employments> employmentList;
        private List<String> tags;
        private Images imageUrls;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Company {

        private String companyName;
        private String introduce;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Images {

        private String companyImg1;
        private String companyImg2;
        private String companyImg3;
        private String companyImg4;
        private String companyImg5;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Employments {
        private Long employmentIdx;
        private String empTitle;
        private Long recReward;
        private Long volReward;
        private String empDeadline;

    }
}
