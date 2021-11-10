package com.example.demo.src.common;


import com.example.demo.config.response.BaseResponse;
import com.example.demo.src.common.model.mainDTO;
import com.example.demo.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;


@RestController
@RequiredArgsConstructor
public class CommonController {

    private final CommonProvider commonProvider;
    private final JwtService jwtService;

    /**
     * 메인 페이지 API
     * [GET] /app
     * @return BaseResponse<mainDTO.ResponseDTO>
     */
    @GetMapping("/app")
    public BaseResponse<mainDTO.ResponseDTO> getCompany(HttpServletRequest request) {

        Long userId = (Long)request.getAttribute("userId");

        mainDTO.ResponseDTO result = commonProvider.getMain(userId);
        return new BaseResponse<>(result);
    }
}
