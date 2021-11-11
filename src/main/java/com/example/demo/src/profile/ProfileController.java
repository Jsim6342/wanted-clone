package com.example.demo.src.profile;

import com.example.demo.com.response.BaseResponse;
import com.example.demo.src.profile.model.req.SeekStatus;
import com.example.demo.src.profile.model.res.ApplicationDTO;
import com.example.demo.src.profile.model.res.GetProfileDTO;
import com.example.demo.src.profile.model.res.MyWantedDTO;
import com.example.demo.src.profile.model.res.OffersRes;
import com.example.demo.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
    public BaseResponse<MyWantedDTO.ResponseDTO> getMyWanted(HttpServletRequest request) {

        Long userId = (Long)request.getAttribute("userId");

        MyWantedDTO.ResponseDTO result = profileProvider.getMyWanted(userId);
        return new BaseResponse<>(result);
    }

    /**
     * 유저 프로필 페이지 조회 API
     * [GET] /app/users/profile
     * @return BaseResponse<GetProfileDTO.ResponseDTO>
     */
    @GetMapping("/profile")
    public BaseResponse<GetProfileDTO.ResponseDTO> getProfile(HttpServletRequest request) {

        Long userId = (Long)request.getAttribute("userId");

        GetProfileDTO.ResponseDTO result = profileProvider.getProfile(userId);
        return new BaseResponse<>(result);
    }

    /**
     * 유저 프로필 구직여부 설정 API
     * [PATCH] /app/users/profile/seek-status
     * @return BaseResponse<String>
     */
    @PatchMapping("/profile/seek-status")
    public BaseResponse<String> modifySeekStatus(@RequestBody SeekStatus seekStatus,
                                                 HttpServletRequest request) {

        Long userId = (Long)request.getAttribute("userId");

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
    public BaseResponse<String> modifyBasicResume(@PathVariable Long resumeId,
                                                  HttpServletRequest request) {

        Long userId = (Long)request.getAttribute("userId");

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
    public BaseResponse<List<ApplicationDTO.ResponseDTO>> getApplicationWriting(HttpServletRequest request) {

        Long userId = (Long)request.getAttribute("userId");

        List<ApplicationDTO.ResponseDTO> result = profileProvider.getApplicationWriting(userId);

        return new BaseResponse<>(result);
    }

    /**
     * 지원 현황 페이지 API
     * [GET] /app/users/profile/applications
     * @return BaseResponse<List<ApplicationDTO.ResponseDTO>>
     */
    @GetMapping("/profile/applications")
    public BaseResponse<List<ApplicationDTO.ResponseDTO>> getApplications(HttpServletRequest request) {

        Long userId = (Long)request.getAttribute("userId");

        List<ApplicationDTO.ResponseDTO> result = profileProvider.getApplications(userId);

        return new BaseResponse<>(result);
    }

    /**
     * 제안 받기 페이지 API
     * [GET] /app/users/profile/suggestions?type=
     * @return BaseResponse<List<OffersRes>>
     */
    @GetMapping("/profile/suggestions")
    public BaseResponse<List<OffersRes>> getOffers(@RequestParam String type,
                                                   HttpServletRequest request) {

        Long userId = (Long)request.getAttribute("userId");

        List<OffersRes> result = profileProvider.getOffers(userId, type);

        return new BaseResponse<>(result);
    }

}
