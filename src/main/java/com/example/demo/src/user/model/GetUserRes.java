package com.example.demo.src.user.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class GetUserRes {
    private long userIdx;
    private String userEmail;
    private String userPassword;
    private String userName;
    private String userPhoneNumber;
    private long basicResumeIdx;
    private String seekStatus;
    private long point;
    private int receiveInfo;
    private int eventAlarm;
    private String authorityLevel;
    private int oauth2;
    private int autoLogin;
    private Date created;
    private Date updated;
    private String status;
}
