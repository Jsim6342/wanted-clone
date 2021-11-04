package com.example.demo.src.employment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employment {
    private Long employmentIdx;
    private Long companyIdx;
    private String empTitle;
    private String companyName;
    private String companyLocation;
    private Long recReward;
    private Long volReward;
    private Long empLiked;
    private Long career;
    private String empContents;
    private String empDeadline;
    private String workLocation;
    private String created;
    private String updated;
    private String status;

    public Employment(String empTitle, Long recReward, Long volReward, String empDeadline) {
        this.empTitle = empTitle;
        this.recReward = recReward;
        this.volReward = volReward;
        this.empDeadline = empDeadline;
    }
}
