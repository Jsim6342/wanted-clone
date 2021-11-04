package com.example.demo.src.company;

import com.example.demo.config.response.BaseResponse;
import com.example.demo.src.company.model.PostCompanyReq;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app/companies")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    /**
     * 기업 등록 API
     * [POST] /app/companies
     * @return BaseResponse<String>
     */
    @PostMapping("")
    public BaseResponse<String> createCompany(@RequestBody PostCompanyReq postCompanyReq) {

        companyService.createCompany(postCompanyReq);
        String result = "";
        return new BaseResponse<>(result);
    }

    /**
     * 기업 상세 조회 API
     * [GET] /app/companies/{companyId}
     * @return BaseResponse<>
     */

    /**
     * 기업 정보 수정 API
     * [PATCH] /app/companies/{companyId}
     * @return BaseResponse<>
     */

    /**
     * 기업 관리 페이지 API
     * [GET] /app/companies/{companyId}/management
     * @return BaseResponse<>
     */



}
