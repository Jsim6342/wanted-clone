package com.example.demo.src.company.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class GetApplicationsDTO {

    //res
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ResponseDTO {

        private applicationDTO applicationDTO;
        private List<file> fileList;
    }

    // 지원서 출력 내용
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class applicationDTO {
        private Long userIdx;
        private String userName;
        private String userEmail;
        private String userPhoneNumber;
        private Long applicationIdx;
        private Long employmentIdx;
        private String recommend;
        private String applicationStatus;
        private String updated;
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
