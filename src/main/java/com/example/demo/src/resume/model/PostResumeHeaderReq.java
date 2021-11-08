package com.example.demo.src.resume.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostResumeHeaderReq {
    private String userName;
    private String userEmail;
    private String userPhoneNumber;
    private String contents;
}
