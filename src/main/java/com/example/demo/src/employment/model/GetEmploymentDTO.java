package com.example.demo.src.employment.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
public class GetEmploymentDTO {
    private GetEmploymentRes getEmploymentRes;
    private String imgUrl;
    private Long empLiked;
}
