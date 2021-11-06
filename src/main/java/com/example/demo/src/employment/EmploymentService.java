package com.example.demo.src.employment;

import com.example.demo.config.exception.BaseException;
import com.example.demo.src.employment.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static com.example.demo.config.response.BaseResponseStatus.*;


@Service
public class EmploymentService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final EmploymentProvider employmentProvider;
    private final EmploymentDao employmentDao;
    private final JwtService jwtService;

    @Autowired
    public EmploymentService(EmploymentProvider employmentProvider, EmploymentDao employmentDao, JwtService jwtService) {
        this.employmentProvider = employmentProvider;
        this.employmentDao = employmentDao;
        this.jwtService = jwtService;
    }

    public PostEmploymentRes createEmployment(PostEmploymentReq postEmploymentReq) throws BaseException{
        try{
            Long employmentIdx = employmentDao.createEmployment(postEmploymentReq);
            return new PostEmploymentRes(employmentIdx);
        }catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public PostEmploymentLikedRes employmentLiked(PostEmploymentLikedReq likedReq) throws BaseException{
        try{
            Long userIdxByJwt = jwtService.getUserIdx();
            Long employmentLikedIdx = employmentDao.employmentLiked(likedReq, userIdxByJwt);
            return new PostEmploymentLikedRes(employmentLikedIdx);

        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public PostEmploymentBookmarkRes employmentBookmark(PostEmploymentBookmarkReq bookmarkReq) throws BaseException{
        try{
            Long userIdxByJwt = jwtService.getUserIdx();
            Long empBookmarkIdx = employmentDao.employmentBookmark(bookmarkReq, userIdxByJwt);
            return new PostEmploymentBookmarkRes(empBookmarkIdx);

        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
