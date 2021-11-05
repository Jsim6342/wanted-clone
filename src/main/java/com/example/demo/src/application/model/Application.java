package com.example.demo.src.application.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Application {
    private Long applicationIdx;
    private Long userIdx;
    private Long employmentIdx;
    private Long resumeIdx;
    private Long attachedFileIdx;
    private String userName;
    private String userEmail;
    private String userPhone;
    private String recommend;
    private String applicationStatus;
    private String created;
    private String updated;
    private String status;
}
