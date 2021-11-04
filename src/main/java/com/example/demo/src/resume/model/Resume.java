package com.example.demo.src.resume.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Resume {
    private Long resumeIdx;
    private Long userIdx;
    private String userName;
    private String userEmail;
    private String userPhoneNumber;
    private String contents;
    private Long openCount;
    private String created;
    private String updated;
    private String status;
}
