package com.example.demo.com.advice;

import com.example.demo.com.exception.BaseException;
import com.example.demo.com.response.BaseResponse;
import com.example.demo.com.exception.BaseResponseStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(annotations = RestController.class)
public class ControllerAdvice {

    // 등록되지 않은 에러
    @ExceptionHandler(value = Exception.class)
    public BaseResponse exception(Exception e) {
        log.error("error class name: {}",e.getClass().getName());
        log.error("error message: {}", e.getMessage());
        e.printStackTrace(); // 어느 부분에서 예외가 발생했는지 알려주는 추적로그 출력
        return new BaseResponse(BaseResponseStatus.NOT_REGISTRATION_ERROR);
    }

    // 기본 커스텀 에러
    @ExceptionHandler(value = BaseException.class)
    public BaseResponse BaseException(BaseException e) {
        return new BaseResponse(e.getStatus());
    }
}
