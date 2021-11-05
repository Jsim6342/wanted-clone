package com.example.demo.src.employment;


import com.example.demo.config.exception.BaseException;
import com.example.demo.config.response.BaseResponse;
import com.example.demo.src.employment.model.PostEmploymentReq;
import com.example.demo.src.employment.model.PostEmploymentRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import static com.example.demo.config.response.BaseResponseStatus.*;

@RestController
@RequestMapping("/app/employments")
public class EmploymentController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final EmploymentProvider employmentProvider;

    @Autowired
    private final EmploymentService employmentService;

    @Autowired
    private final JwtService jwtService;

    public EmploymentController(EmploymentProvider employmentProvider, EmploymentService employmentService, JwtService jwtService) {
        this.employmentProvider = employmentProvider;
        this.employmentService = employmentService;
        this.jwtService = jwtService;
    }

    /**
     * 채용공고 등록 API
     * [POST]
     */
    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostEmploymentRes> createEmployment(@RequestBody PostEmploymentReq postEmploymentReq){
        // 채용공고 이름을 입력 안했을 때
        if(postEmploymentReq.getEmpTitle().equals("")){
            return new BaseResponse<>(POST_EMPLOYMENT_EMPTY_TITLE);
        }
        // 회사이름을 입력 안했을 때
        if(postEmploymentReq.getCompanyName().equals("")){
            return new BaseResponse<>(POST_EMPLOYMENT_EMPTY_COMPANY_NAME);
        }
        // 회사 위치를 입력 안했을 때
        if(postEmploymentReq.getCompanyLocation().equals("")){
            return new BaseResponse<>(POST_EMPLOYMENT_EMPTY_COMPANY_LOCATION);
        }
        // 추천인 보상금을 입력 안했을 때
        if(postEmploymentReq.getRecReward().equals("")){
            return new BaseResponse<>(POST_EMPLOYMENT_EMPTY_REC_REWARD);
        }
        // 지원자 보상금을 입력 안했을 때
        if(postEmploymentReq.getVolReward().equals("")){
            return new BaseResponse<>(POST_EMPLOYMENT_EMPTY_VOL_REWARD);
        }
        // 채용공고 본문을 입력 안했을 때
        if(postEmploymentReq.getEmpContents().equals("")){
            return new BaseResponse<>(POST_EMPLOYMENT_EMPTY_CONTENTS);
        }
        // 채용공고 마감일을 입력 안했을 때
        if(postEmploymentReq.getEmpDeadline().equals("")){
            return new BaseResponse<>(POST_EMPLOYMENT_EMPTY_DEADLINE);
        }
        // 채용시 근무지를 입력 안했을 때
        if(postEmploymentReq.getWorkLocation().equals("")){
            return new BaseResponse<>(POST_EMPLOYMENT_EMPTY_WORK_LOCATION);
        }
        try{
            PostEmploymentRes postEmploymentRes = employmentService.createEmployment(postEmploymentReq);
            return new BaseResponse<>(postEmploymentRes);
        }catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
