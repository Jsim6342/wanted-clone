package com.example.demo.src.resume;

import com.example.demo.config.exception.BaseException;
import com.example.demo.config.response.BaseResponse;
import com.example.demo.src.company.model.GetResumeDTO;
import com.example.demo.src.resume.model.PostResumeHeaderReq;
import com.example.demo.src.resume.model.PostResumeReq;
import com.example.demo.src.resume.model.PostResumeRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/app/resumes")
public class ResumeController {

    private final ResumeProvider resumeProvider;
    private final ResumeService resumeService;

    @Autowired
    public ResumeController(ResumeProvider resumeProvider, ResumeService resumeService) {
        this.resumeProvider = resumeProvider;
        this.resumeService = resumeService;
    }

    /**
     * 이력서 상세 조회 API
     * [Get] /app/resumes/{resumesId}
     * @return BaseResponse<GetResumeDTO.ResponseDTO>
     */
    @GetMapping("/{resumeId}")
    public BaseResponse<GetResumeDTO.ResponseDTO> getResume(@PathVariable Long resumeId) {

        GetResumeDTO.ResponseDTO result = resumeProvider.getResume(resumeId);
        return new BaseResponse<>(result);
    }

    /**
     * 이력서 생성 API
     * [POST]
     */
    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostResumeRes> createResume(@RequestBody PostResumeReq postResumeReq){
        try{
            PostResumeRes postResumeRes = resumeService.createResume(postResumeReq);
            return new BaseResponse<>(postResumeRes);
        }catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
