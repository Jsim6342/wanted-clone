package com.example.demo.src.employment.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostEmploymentReq {
    private Long companyIdx;
    private String empTitle;
    private String jobGroup;
    private String companyName;
    private String companyLocation;
    private Long recReward;
    private Long volReward;
    private String empContents;
    private String empDeadline;
    private String workLocation;
}
