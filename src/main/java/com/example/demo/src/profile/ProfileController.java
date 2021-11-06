package com.example.demo.src.profile;

import com.example.demo.config.exception.BaseException;
import com.example.demo.config.response.BaseResponse;
import com.example.demo.src.profile.model.MyWantedDTO;
import com.example.demo.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.demo.config.response.BaseResponseStatus.JWT_ERROR;

@RestController
@RequestMapping("/app/users")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileProvider profileProvider;
    private final JwtService jwtService;

    /**
     * My원티드 페이지 API
     * [GET] /app/users/my-wanted
     * @return BaseResponse<String>
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

}
