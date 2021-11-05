package com.example.demo.src.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.exception.BaseException;
import com.example.demo.config.response.BaseResponse;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


import static com.example.demo.config.response.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.isRegexEmail;

@RestController
@RequestMapping("/app/users")
public class UserController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final UserProvider userProvider;
    @Autowired
    private final UserService userService;
    @Autowired
    private final JwtService jwtService;




    public UserController(UserProvider userProvider, UserService userService, JwtService jwtService){
        this.userProvider = userProvider;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    /**
     * JWT 에서 추출한 idx로 회원정보 조회 API
     * [GET] /users
     * @return BaseResponse<GetUserRes>
     */
    //Query String
    @ResponseBody
    @GetMapping("")
    public BaseResponse<GetUserRes> getUsers() throws BaseException {
        Long userIdxByJwt = jwtService.getUserIdx();
        try{
            GetUserRes getUsersRes = userProvider.getUser(userIdxByJwt);
            return new BaseResponse<>(getUsersRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     *  JWT 에서 추출한 idx로 회원정보 수정 API
     * [PATCH]
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("")
    public BaseResponse<String> modifyUserInfo(@RequestBody PatchUserReq patchUserReq){
        try {
            userService.modifyUserInfo(patchUserReq);

            String result = "";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }



    /**
     * 회원가입 API
     * [POST] /users
     * @return BaseResponse<PostUserRes>
     */
    // Body
    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostUserRes> createUser(@RequestBody PostUserReq postUserReq) {
        //닉네임을 입력 안했을 때
        if(postUserReq.getUserName().equals("")){
            return new BaseResponse<>(POST_USERS_EMPTY_NAME);
        }
        //이메일을 입력 안했을 때
        if(postUserReq.getUserEmail().equals("")){
            return new BaseResponse<>(POST_USERS_EMPTY_EMAIL);
        }
        //이메일 정규표현
        if(!isRegexEmail(postUserReq.getUserEmail())){
            return new BaseResponse<>(POST_USERS_INVALID_EMAIL);
        }
        try{
            PostUserRes postUserRes = userService.createUser(postUserReq);
            return new BaseResponse<>(postUserRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 이메일로 회원 조회 API
     * [GET] /users/:userNickname
     * @return BaseResponse<GetUserRes>
     */
    @ResponseBody
    @GetMapping("/userEmail/{userEmail}")
    public BaseResponse<GetUserRes> getUserByEmail(@PathVariable("userEmail") String email){
        try{
            GetUserRes getUsersRes = userProvider.getUsersByEmail(email);
            return new BaseResponse<>(getUsersRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }



    /**
     * 로그인 API
     * [POST] /users/logIn
     * @return BaseResponse<PostLoginRes>
     */
    @ResponseBody
    @PostMapping("/logIn")
    public BaseResponse<PostLoginRes> logIn(@RequestBody PostLoginReq postLoginReq){
        // 이메일 입력 안했을 때 validation
        if(postLoginReq.getUserEmail().equals("")){
            return new BaseResponse<>(POST_USERS_EMPTY_EMAIL);
        }
        // 비밀번호 입력 안했을 때 validation
        if(postLoginReq.getUserPassword().equals("")){
            return new BaseResponse<>(POST_USERS_EMPTY_PASSWORD);
        }
        try{
            PostLoginRes postLoginRes = userProvider.logIn(postLoginReq);
            return new BaseResponse<>(postLoginRes);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }




}
