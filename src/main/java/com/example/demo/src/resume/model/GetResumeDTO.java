package com.example.demo.src.resume.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class GetResumeDTO {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ResponseDTO {
        private Resume resume;
        private List<School> schoolList;
        private List<Career> careerList;
        private List<String> skillList;
        private List<LanguageDTO> languageList;
        private Portfolio portfolio;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Resume {
        private String userName;
        private String userEmail;
        private String userPhoneNumber;
        private String contents;
    }

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
    public static class LanguageDTO {
        private Language language;
        private List<LanguageTest> languageTestList;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Language {
        private Long foreignLanguageIdx;
        private String language;
        private String languageLevel;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LanguageTest {
        private String testName;
        private String score;
        private String acquisitionDate;
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
