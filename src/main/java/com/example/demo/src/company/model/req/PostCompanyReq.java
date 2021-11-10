package com.example.demo.src.company.model.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostCompanyReq {

    @NotBlank(message = "회사 이름을 입력해주세요.")
    private String companyName;

    @NotBlank(message = "회사 국가를 입력해주세요.")
    private String companyNation;

    @NotBlank(message = "회사 지역을 입력해주세요.")
    private String companyLocation;

    @NotBlank(message = "회사 주소를 입력해주세요.")
    private String companyAddress;

    @Pattern(regexp = "^(\\d{3,3})+[-]+(\\d{2,2})+[-]+(\\d{5,5})", message = "사업자등록번호 형식과 맞지 않습니다.")
    private String registrationNum;

    @NotBlank(message = "매출액을 입력해주세요.")
    private String sales;

    @NotBlank(message = "산업군을 입력해주세요.")
    private String industryGroup;

    @NotNull(message = "직원수를 입력해주세요.")
    private Long companySize;

    @NotBlank(message = "회사/서비스 소개를 입력해주세요.")
    private String introduce;

    @PastOrPresent(message = "정확한 설립일을 입력해주세요.")
    private String establishment;

    @Email(message = "정확한 이메일을 입력해주세요.")
    private String companyEmail;

    @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "정확한 휴대폰 번호를 입력해주세요.")
    private String companyPhone;

    private String companyUrl;
    private String response;
}
