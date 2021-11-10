package com.example.demo.src.profile;

import com.example.demo.config.exception.BaseException;
import com.example.demo.config.response.BaseResponse;
import com.example.demo.src.profile.model.req.SeekStatus;
import com.example.demo.src.profile.model.res.ApplicationDTO;
import com.example.demo.src.profile.model.res.GetProfileDTO;
import com.example.demo.src.profile.model.res.MyWantedDTO;
import com.example.demo.src.profile.model.res.OffersRes;
import com.example.demo.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.response.BaseResponseStatus.JWT_ERROR;

@RestController
@RequestMapping("/app/users")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileProvider profileProvider;
    private final ProfileService profileService;
    private final JwtService jwtService;

    /**
     * My원티드 페이지 API
     * [GET] /app/users/my-wanted
     * @return BaseResponse<MyWantedDTO.ResponseDTO>
     */
    @GetMapping("/my-wanted")
    public BaseResponse<MyWantedDTO.ResponseDTO> getMyWanted() {

        Long userId = 0L;
        try{
            userId = jwtService.getUserIdx();
        }catch (BaseException e) {
            new BaseException(JWT_ERROR);
        }

        MyWantedDTO.ResponseDTO result = profileProvider.getMyWanted(userId);
        return new BaseResponse<>(result);
    }

    /**
     * 유저 프로필 페이지 조회 API
     * [GET] /app/users/profile
     * @return BaseResponse<GetProfileDTO.ResponseDTO>
     */
    @GetMapping("/profile")
    public BaseResponse<GetProfileDTO.ResponseDTO> getProfile() {

        Long userId = 0L;
        try{
            userId = jwtService.getUserIdx();
        }catch (BaseException e) {
            new BaseException(JWT_ERROR);
        }

        GetProfileDTO.ResponseDTO result = profileProvider.getProfile(userId);
        return new BaseResponse<>(result);
    }

    /**
     * 유저 프로필 구직여부 설정 API
     * [PATCH] /app/users/profile/seek-status
     * @return BaseResponse<String>
     */
    @PatchMapping("/profile/seek-status")
    public BaseResponse<String> modifySeekStatus(@RequestBody SeekStatus seekStatus) {

        Long userId = 0L;
        try{
            userId = jwtService.getUserIdx();
        }catch (BaseException e) {
            new BaseException(JWT_ERROR);
        }

        profileService.modifySeekStatus(userId, seekStatus);

        String result = "";
        return new BaseResponse<>(result);
    }

    /**
     * 유저 기본 이력서 수정 API
     * [PATCH] /app/users/profile/{resumeId}
     * @return BaseResponse<String>
     */
    @PatchMapping("/profile/{resumeId}")
    public BaseResponse<String> modifyBasicResume(@PathVariable Long resumeId) {

        Long userId = 0L;
        try{
            userId = jwtService.getUserIdx();
        }catch (BaseException e) {
            new BaseException(JWT_ERROR);
        }

        profileService.modifyBasicResume(userId, resumeId);

        String result = "";
        return new BaseResponse<>(result);
    }

    /**
     * 작성중인 지원 현황 페이지 API
     * [GET] /app/users/profile/applications/write
     * @return BaseResponse<List<ApplicationDTO.ResponseDTO>>
     */
    @GetMapping("/profile/applications/write")
    public BaseResponse<List<ApplicationDTO.ResponseDTO>> getApplicationWriting() {

        Long userId = 0L;
        try{
            userId = jwtService.getUserIdx();
        }catch (BaseException e) {
            new BaseException(JWT_ERROR);
        }

        List<ApplicationDTO.ResponseDTO> result = profileProvider.getApplicationWriting(userId);

        return new BaseResponse<>(result);
    }

    /**
     * 지원 현황 페이지 API
     * [GET] /app/users/profile/applications
     * @return BaseResponse<List<ApplicationDTO.ResponseDTO>>
     */
    @GetMapping("/profile/applications")
    public BaseResponse<List<ApplicationDTO.ResponseDTO>> getApplications() {

        Long userId = 0L;
        try{
            userId = jwtService.getUserIdx();
        }catch (BaseException e) {
            new BaseException(JWT_ERROR);
        }

        List<ApplicationDTO.ResponseDTO> result = profileProvider.getApplicatios(userId);

        return new BaseResponse<>(result);
    }

    /**
     * 제안 받기 페이지 API
     * [GET] /app/users/profile/suggestions?type=
     * @return BaseResponse<List<OffersRes>>
     */
    @GetMapping("/profile/suggestions")
    public BaseResponse<List<OffersRes>> getOffers(@RequestParam String type) {

        Long userId = 0L;
        try{
            userId = jwtService.getUserIdx();
        }catch (BaseException e) {
            new BaseException(JWT_ERROR);
        }

        List<OffersRes> result = profileProvider.getOffers(userId, type);

        return new BaseResponse<>(result);
    }

}
