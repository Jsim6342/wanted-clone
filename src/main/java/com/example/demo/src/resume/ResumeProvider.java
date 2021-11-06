package com.example.demo.src.resume;

import com.example.demo.src.company.model.GetResumeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResumeProvider {

    private final ResumeDao resumeDao;

    public GetResumeDTO.ResponseDTO getResume(Long resumeId) {
        return resumeDao.getResume(resumeId);
    }
}
