package com.example.demo.src.company.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetApplicationsRes {
    private Long applicationIdx;
    private String applicationStatus;
    private String userName;
    private String userEmail;
    private String userPhone;
}
