package com.example.demo.src.company;

import com.example.demo.config.response.BaseResponse;
import com.example.demo.src.company.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
     * @return BaseResponse<String>
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

    /**
     * 기업에 지원한 지원서 출력 페이지 API
     * [GET] /app/companies/{companyId}/applications
     * @return BaseResponse<List<GetApplicationsRes>>
     */
    @GetMapping("/{companyId}/applications")
    public BaseResponse<List<GetApplicationsRes>> getApplications(@PathVariable Long companyId) {
        List<GetApplicationsRes> result = companyProvider.getApplications(companyId);
        return new BaseResponse<>(result);
    }


    /**
     * 기업에 지원한 지원서 출력 페이지 API
     * [GET] /app/companies/{companyId}/applications/{applicationId}
     * @return BaseResponse<GetApplicationsDTO.ResponseDTO>
     */
    @GetMapping("/{companyId}/applications/{applicationId}")
    public BaseResponse<GetApplicationsDTO.ResponseDTO> getApplication(@PathVariable Long companyId,
                                                                 @PathVariable Long applicationId) {
        GetApplicationsDTO.ResponseDTO result = companyProvider.getApplication(applicationId);
        return new BaseResponse<>(result);
    }

    /**
     * 지원서 결정 API
     * [PATCH] /app/companies/{companyId}/applications/{applicationId}
     * @return BaseResponse<String>
     */
    @PatchMapping("/{companyId}/applications/{applicationId}")
    public BaseResponse<String> setApplicationStatus(@PathVariable Long companyId,
                                                     @PathVariable Long applicationId,
                                                     @RequestBody String status) {
        companyService.setApplicationStatus(applicationId, status);
        String result = "";
        return new BaseResponse<>(result);
    }

    /**
     * 기업-회원 이력서 조회 API
     * [GET] /app/companies/{companyId}/resumes
     * @return BaseResponse<>
     */
    @GetMapping("/{companyId}/resumes")
    public BaseResponse<List<GetResumeDTO.ResponseDTO>> getResumes(@PathVariable Long companyId) {

        Long userId = 6L; // 더미 데이터
        List<GetResumeDTO.ResponseDTO> result = companyProvider.getResumes(userId);
        return new BaseResponse<>(result);
    }

}
