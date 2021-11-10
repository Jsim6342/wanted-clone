package com.example.demo.src.company;

import com.example.demo.config.response.BaseResponse;
import com.example.demo.src.company.model.*;
import com.example.demo.src.company.model.req.ApplicationStatus;
import com.example.demo.src.company.model.req.PatchCompanyReq;
import com.example.demo.src.company.model.req.PostCompanyReq;
import com.example.demo.src.company.model.res.*;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    public BaseResponse<String> createCompany(@Validated @RequestBody PostCompanyReq postCompanyReq,
                                              HttpServletRequest request) throws Exception {

        Long userId = (Long)request.getAttribute("userId");

        companyService.createCompany(userId, postCompanyReq);
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
    public BaseResponse<String> modifyCompany(@Validated @RequestBody PatchCompanyReq postCompanyReq,
                                                       @PathVariable Long companyId
                                                ,HttpServletRequest request) throws Exception {

        Long userId = (Long)request.getAttribute("userId");

        companyService.modifyCompany(userId, companyId, postCompanyReq);
        String result = "";
        return new BaseResponse<>(result);
    }

    /**
     * 기업 관리 페이지 API
     * [GET] /app/companies/{companyId}/management
     * @return BaseResponse<>
     */
    @GetMapping("/{companyId}/management")
    public BaseResponse<Company> getCompanyManagement(@PathVariable Long companyId,
                                                      HttpServletRequest request) throws Exception{

        Long userId = (Long)request.getAttribute("userId");

        Company result = companyProvider.getCompanyManagement(userId, companyId);
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
                                                     @RequestBody ApplicationStatus status) {
        companyService.setApplicationStatus(applicationId, status);
        String result = "";
        return new BaseResponse<>(result);
    }

    /**
     * 기업-회원 이력서 리스트 조회 API
     * [GET] /app/companies/{companyId}/resumes
     * @return BaseResponse<List<GetResumeDTO.ResponseDTO>>
     */
    @GetMapping("/{companyId}/resumes")
    public BaseResponse<List<GetResumeListDTO.ResponseDTO>> getResumes(@PathVariable Long companyId) {

        List<GetResumeListDTO.ResponseDTO> result = companyProvider.getResumes();
        return new BaseResponse<>(result);
    }

    /**
     * 기업-회원 이력서 상세 조회 API
     * [GET] /app/companies/{companyId}/resumes/{resumeId}
     * @return BaseResponse<GetResumeDTO.ResponseDTO>
     */
    @GetMapping("/{companyId}/resumes/{resumeId}")
    public BaseResponse<GetResumeDTO.ResponseDTO> getResume(@PathVariable Long companyId,
                                                            @PathVariable Long resumeId) {

        GetResumeDTO.ResponseDTO result = companyProvider.getResume(resumeId);
        return new BaseResponse<>(result);
    }

    /**
     * 기업 이력서 원해요 등록 API
     * [POST] /app/companies/{companyId}/resumes/{resumeId}
     * @return BaseResponse<String>
     */
    @PostMapping("/{companyId}/resumes/{resumeId}")
    public BaseResponse<String> createResumeLike(@PathVariable Long companyId,
                                                 @PathVariable Long resumeId) {

        companyService.createResumeLike(companyId, resumeId);
        String result = "";
        return new BaseResponse<>(result);
    }

    /**
     * 기업 이력서 원해요 삭제 API
     * [DELETE] /app/companies/{companyId}/resumes/{resumeId}
     * @return BaseResponse<String>
     */
    @DeleteMapping("/{companyId}/resumes/{resumeId}")
    public BaseResponse<String> deleteResumeLike(@PathVariable Long companyId,
                                                 @PathVariable Long resumeId) {

        companyService.deleteResumeLike(companyId, resumeId);
        String result = "";
        return new BaseResponse<>(result);
    }

    /**
     * 기업 입사 제안 API
     * [POST] /app/companies/{companyId}/resumes/offer/{userId}
     * @return BaseResponse<String>
     */
    @PostMapping("/{companyId}/resumes/offer/{userId}")
    public BaseResponse<String> createOffer(@PathVariable Long companyId,
                                            @PathVariable Long userId) {

        companyService.createOffer(companyId, userId);
        String result = "";
        return new BaseResponse<>(result);
    }


    /**
     * 기업 삭제 API
     * [DELETE] /app/companies/{companyId}
     * @return BaseResponse<String>
     */
    @DeleteMapping("/{companyId}")
    public BaseResponse<String> createOffer(@PathVariable Long companyId,
                                            HttpServletRequest request) throws Exception {

        Long userId = (Long)request.getAttribute("userId");

        companyService.deleteCompany(userId, companyId);
        String result = "";
        return new BaseResponse<>(result);
    }


}
