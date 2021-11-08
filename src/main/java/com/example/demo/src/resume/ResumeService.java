package com.example.demo.src.resume;

import com.example.demo.config.exception.BaseException;
import com.example.demo.src.resume.model.PostResumeHeaderReq;
import com.example.demo.src.resume.model.PostResumeReq;
import com.example.demo.src.resume.model.PostResumeRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static com.example.demo.config.response.BaseResponseStatus.*;


@Service
public class ResumeService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ResumeProvider resumeProvider;
    private final ResumeDao resumeDao;
    private final JwtService jwtService;


    @Autowired
    public ResumeService(ResumeProvider resumeProvider, ResumeDao resumeDao, JwtService jwtService) {
        this.resumeProvider = resumeProvider;
        this.resumeDao = resumeDao;
        this.jwtService = jwtService;
    }

    public PostResumeRes createResume(PostResumeReq postResumeReq) throws BaseException{
        try{
            Long userIdxByJwt = jwtService.getUserIdx();
            Long resumeIdx = resumeDao.createResume(postResumeReq, userIdxByJwt);
            System.out.println(resumeIdx);
            return new PostResumeRes(resumeIdx);
        } catch (BaseException exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
