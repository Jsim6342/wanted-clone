package com.example.demo.src.user.model;

import lombok.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long userIdx;
    private String userEmail;
    private String userPassword;
    private String userName;
    private String userPhoneNumber;
    private Long basicResumeIdx;
    private String seekStatus;
    private Long point;
    private Long receiveInfo;
    private Long eventAlarm;
    private String authorityLevel;
    private Long oauth2;
    private Long autoLogin;
    private Date created;
    private Date updated;
    private String status;
}