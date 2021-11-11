package com.example.demo.src.employment;


import com.example.demo.com.annotation.UnAuth;
import com.example.demo.com.exception.BaseException;
import com.example.demo.com.response.BaseResponse;
import com.example.demo.src.employment.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.com.exception.BaseResponseStatus.*;

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

    /**
     * 채용공고 페이지 상세 API
     * [GET]
     */
    @UnAuth
    @ResponseBody
    @GetMapping("{employmentIdx}")
    public BaseResponse<GetEmploymentDTO> getEmploymentByEmploymentIdx(@PathVariable("employmentIdx") Long employmentIdx){
        try{
            GetEmploymentDTO getEmploymentDTO = employmentProvider.getEmploymentByEmploymentIdx(employmentIdx);
            return new BaseResponse<>(getEmploymentDTO);
        } catch (EmptyResultDataAccessException emptyException) {
            return new BaseResponse<>(RESULT_ROWS_EMPTY);
        } catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 채용공고 페이지 조회(태그,지역,경력) API
     * [GET]
     */
    @UnAuth
    @ResponseBody
    @GetMapping("")
    public BaseResponse<List<GetEmploymentPageRes>> getEmploymentPage(
            @RequestParam(value = "tag", required = false) String tag,
            @RequestParam(value = "location", required = false) String location,
            @RequestParam(value = "years", required = false) Long year){
        if(tag.equals("")){
            tag = "인원 급성장";
        }
        if(location.equals("")){
            location = "서울";
        }
        if(year == null){
            year = (long)0;
        }

        try{
            List<GetEmploymentPageRes> getEmploymentPageRes = employmentProvider.getEmploymentPage(tag, location, year);
            return new BaseResponse<>(getEmploymentPageRes);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 채용공고 좋아요 API
     * [POST]
     */
    @ResponseBody
    @PostMapping("/{employmentId}/likes")
    public BaseResponse<PostEmploymentLikedRes> employmentLiked(@PathVariable("employmentId") Long employmentIdx){
        try{
            PostEmploymentLikedRes likedRes = employmentService.employmentLiked(employmentIdx);
            return new BaseResponse<>(likedRes);
        }catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 채용공고 좋아요 삭제 API
     * [DELETE]
     */
    @ResponseBody
    @DeleteMapping("/{employmentId}/likes")
    public BaseResponse<PostEmploymentLikedDelRes> employmentLikedDelete(@PathVariable("employmentId") Long employmentIdx){
        try{
            PostEmploymentLikedDelRes likedDelRes = employmentService.employmentLikedDelete(employmentIdx);
            return new BaseResponse<>(likedDelRes);
        } catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 채용공고 북마크 API
     * [POST]
     */
    @ResponseBody
    @PostMapping("/{employmentId}/bookmarks")
    public BaseResponse<PostEmploymentBookmarkRes> employmentBookmark(@PathVariable("employmentId") Long employmentIdx){
        try{
            PostEmploymentBookmarkRes bookmarkRes = employmentService.employmentBookmark(employmentIdx);
            return new BaseResponse<>(bookmarkRes);
        }catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 채용공고 북마크 삭제 API
     * [DELETE]
     */
    @ResponseBody
    @DeleteMapping("/{employmentId}/bookmarks")
    public BaseResponse<PostEmploymentBookmarkDelRes> employmentBookmarkDelete(@PathVariable("employmentId") Long employmentIdx){
        try{
            PostEmploymentBookmarkDelRes bookmarkDelRes = employmentService.employmentBookmarkDelete(employmentIdx);
            return new BaseResponse<>(bookmarkDelRes);
        }catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
