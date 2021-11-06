package com.example.demo.src.resume;

import com.example.demo.config.response.BaseResponse;
import com.example.demo.src.company.model.GetResumeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/resumes")
@RequiredArgsConstructor
public class ResumeController {

    private final ResumeProvider resumeProvider;

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

}
