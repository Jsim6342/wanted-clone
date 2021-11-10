package com.example.demo.src.profile.model.res;

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
        private Long resumeId;
        private String title;
        private String schoolName;
        private String major;
        private String companyName;
        private String departmentName;
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
        private Long specializedId;
        private String field;
        private String jobGroup1;
        private String jobGroup2;
        private String jobGroup3;
        private String career;
    }


}
