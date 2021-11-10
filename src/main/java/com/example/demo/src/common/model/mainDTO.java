package com.example.demo.src.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


public class mainDTO {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ResponseDTO {
        private List<Banner> banner;
        private List<Employment> employmentList;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Banner {
        private String imageUrl;
        private String linkUrl;
        private String title;
        private String subTitle;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Employment {
        private String imageUrl;
        private Long employmentId;
        private String empTitle;
        private String companyName;
        private String companyLocation;
        private Long reward;
    }



}
