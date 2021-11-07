package com.example.demo.src.profile.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ApplicationDTO {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ResponseDTO {
        private String imageUrl;
        private Employment employment;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Employment {
        private Long applicationId;
        private Long employmentId;
        private String companyName;
        private String applicationTitle;
        private String writingTime;
        private String applicationStatus;
    }



}
