package com.example.demo.src.company;

import com.example.demo.config.response.BaseResponse;
import com.example.demo.src.company.model.Company;
import com.example.demo.src.company.model.PatchCompanyReq;
import com.example.demo.src.company.model.PostCompanyReq;
import com.example.demo.src.company.model.GetCompanyDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/app/companies")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;
    private final CompanyProvider companyProvider;

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
     * @return BaseResponse<GetCompanyRes>
     */
    @GetMapping("/{companyId}")
    public BaseResponse<GetCompanyDTO.ResponseDTO> getCompany(@PathVariable Long companyId) {
        GetCompanyDTO.ResponseDTO result = companyProvider.getCompany(companyId);
        return new BaseResponse<>(result);
    }


    /**
     * 기업 정보 수정 API
     * [PATCH] /app/companies/{companyId}
     * @return BaseResponse<>
     */
    @PatchMapping("/{companyId}")
    public BaseResponse<String> modifyCompany(@RequestBody PatchCompanyReq postCompanyReq,
                                                       @PathVariable Long companyId) {
        companyService.modifyCompany(companyId, postCompanyReq);
        String result = "";
        return new BaseResponse<>(result);
    }

    /**
     * 기업 관리 페이지 API
     * [GET] /app/companies/{companyId}/management
     * @return BaseResponse<>
     */
    @GetMapping("/{companyId}/management")
    public BaseResponse<Company> getCompanyManagement(@PathVariable Long companyId) {
        Company result = companyProvider.getCompanyManagement(companyId);
        return new BaseResponse<>(result);
    }


}
