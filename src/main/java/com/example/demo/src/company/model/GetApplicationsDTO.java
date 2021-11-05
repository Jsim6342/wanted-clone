package com.example.demo.src.company.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class GetApplicationsDTO {

    //res
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ResponseDTO {
        private Member member;
        private Employment employment;
        private Resume resume;
        private List<file> fileList;
    }

    //회원
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Member {
        private Long userIdx;
        private String userName;
        private String userEmail;
        private String userPhoneNumber;
    }

    //지원서
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Employment {
        private Long applicationIdx;
        private Long employmentIdx;
        private String recommend;
        private String applicationStatus;
        private String applicationDate; //updated
    }

    //이력서
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Resume {
        private Long resumeIdx;
        private String title;
    }

    //첨부파일
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class file {
        private Long fileIdx;
        private String fileName;
        private String savePath;
    }
}
