package com.example.demo.src.resume.model;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostResumeReq {
        private String userName;
        private String userEmail;
        private String userPhoneNumber;
        private String contents;
        private List<School> schoolList;
        private List<Career> careerList;
        private List<Language> languageList;
        private List<Portfolio> portfoliosList;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class School {
        private String schoolName;
        private String major;
        private String subjectContents;
        private String entranced;
        private String graduated;
        private Long inSchool;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Career {
        private String companyName;
        private String departmentName;
        private String outcomeName;
        private String outcomeStart;
        private String outcomeEnd;
        private String outcomeContents;
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Language {
        private String language;
        private String languageLevel;
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Portfolio {
        private String portfolioUrl1;
        private String portfolioUrl2;
        private String portfolioUrl3;

    }

}

