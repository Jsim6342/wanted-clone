package com.example.demo.src.profile.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class GetProfileDTO {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ResponseDTO {
        private User user;
        private BasicResume basicResume;
        private SpecializedDTO specializedDTO;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class User {
        private String userName;
        private String userEmail;
        private String userPhoneNumber;
        private Long point;
        private Long basicResumeId;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BasicResume {
        private Long resume_idx;
        private String title;
        private String school_name;
        private String major;
        private String company_name;
        private String department_name;
        private String contents;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class SpecializedDTO {
        private Specialized specialized;
        private List<String> skillList;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Specialized {
        private Long specialized_idx;
        private String field;
        private String job_group_1;
        private String job_group_2;
        private String job_group_3;
        private String career;
    }


}
