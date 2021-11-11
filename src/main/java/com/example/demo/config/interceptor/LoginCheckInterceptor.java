package com.example.demo.config.interceptor;


import com.example.demo.config.annotation.UnAuth;
import com.example.demo.config.exception.BaseException;
import com.example.demo.config.exception.BaseResponseStatus;
import com.example.demo.utils.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
public class LoginCheckInterceptor implements HandlerInterceptor {

    private final JwtService jwtService;
    private final ObjectMapper objectMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // Preflight Request 체크
        if (isPreflightRequest(request)) {
            return true;
        }


        // === 비인가 API 로직 ===
        if(checkAnnotation(handler, UnAuth.class)) {

            // 비로그인 로직
            if(request.getHeader("X-ACCESS-TOKEN")==null) {
                request.setAttribute("userId", 0);
                return true;
            }

            // 로그인 로직
            request.setAttribute("userId", jwtService.getUserIdx());
            return true;
        }

        // === 인가 API 로직 ===

        // 비로그인 로직
        if(request.getHeader("X-ACCESS-TOKEN")==null) {
            throw new BaseException(BaseResponseStatus.REQUEST_LOGIN);
        }

        // 로그인 로직
        Long userId = jwtService.getUserIdx();
        request.setAttribute("userId", userId);
        return true;

    }

    private boolean checkAnnotation(Object handler, Class clazz) {

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        if(handlerMethod.getMethodAnnotation(clazz) != null || handlerMethod.getMethodAnnotation(clazz) != null) {
            return true;
        }
        return false;
    }

    private boolean isPreflightRequest(HttpServletRequest request) {
        return isOptions(request) && hasHeaders(request) && hasMethod(request) && hasOrigin(request);
    }

    private boolean isOptions(HttpServletRequest request) {
        return request.getMethod().equalsIgnoreCase(HttpMethod.OPTIONS.toString());
    }

    private boolean hasHeaders(HttpServletRequest request) {
        return Objects.nonNull(request.getHeader("Access-Control-Request-Headers"));
    }

    private boolean hasMethod(HttpServletRequest request) {
        return Objects.nonNull(request.getHeader("Access-Control-Request-Method"));
    }

    private boolean hasOrigin(HttpServletRequest request) {
        return Objects.nonNull(request.getHeader("Origin"));
    }

}
