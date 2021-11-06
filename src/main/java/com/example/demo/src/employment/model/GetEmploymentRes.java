package com.example.demo.src.employment.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class GetEmploymentRes {
    private Long employmentIdx;
    private Long companyIdx;
    private String empTitle;
    private String jobGroup;
    private String companyName;
    private String companyLocation;
    private Long recReward;
    private Long volReward;
    private Long career;
    private String empContents;
    private String empDeadline;
    private String workLocation;
    private Date created;
    private Date updated;
    private String status;
}
