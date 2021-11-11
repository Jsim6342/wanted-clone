package com.example.demo.com.exception;

import lombok.Getter;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {
    /**
     * 1000 : 요청 성공
     */
    SUCCESS(true, 1000, "요청에 성공하였습니다."),


    /**
     * 2000 : Request 오류
     */
    // Common
    REQUEST_LOGIN(false, 2000, "로그인이 필요한 서비스 입니다."),
    REQUEST_ERROR(false, 2001, "입력값을 확인해주세요."),
    EMPTY_JWT(false, 2002, "JWT가 존재하지 않습니다."),
    INVALID_JWT(false, 2003, "유효하지 않은 로그인 정보입니다."),
    INVALID_USER_JWT(false,2004,"권한이 없는 유저의 접근입니다."),
    JWT_ERROR(false, 2005, "JWT 관련 에러입니다."),

    // users
    USERS_EMPTY_USER_ID(false, 2010, "유저 아이디 값을 확인해주세요."),

    // [POST] /users
    POST_USERS_EMPTY_NAME(false,2011,"이름을 입력해주세요."),
    POST_USERS_EMPTY_PASSWORD(false,2012,"비밀번호를 입력해주세요"),
    POST_USERS_EMPTY_EMAIL(false, 2015, "이메일을 입력해주세요."),
    POST_USERS_INVALID_EMAIL(false, 2016, "이메일 형식을 확인해주세요."),
    POST_USERS_EXISTS_EMAIL(false,2017,"중복된 이메일입니다."),

    // [POST] /employments
    POST_EMPLOYMENT_EMPTY_TITLE(false, 2100, "채용공고 제목을 입력하세요."),
    POST_EMPLOYMENT_EMPTY_COMPANY_NAME(false, 2101, "회사 이름을 입력하세요."),
    POST_EMPLOYMENT_EMPTY_COMPANY_LOCATION(false, 2102, "회사 위치를 입력하세요."),
    POST_EMPLOYMENT_EMPTY_REC_REWARD(false, 2103, "추천인 채용보상금을 입력하세요."),
    POST_EMPLOYMENT_EMPTY_VOL_REWARD(false, 2104, "지원자 채용보상금을 입력하세요."),
    POST_EMPLOYMENT_EMPTY_CONTENTS(false, 2105, "채용공고 본문을 입력하세요."),
    POST_EMPLOYMENT_EMPTY_DEADLINE(false, 2106, "채용공고 마감일을 입력하세요."),
    POST_EMPLOYMENT_EMPTY_WORK_LOCATION(false, 2107, "근무지 위치를 입력하세요."),

    // profile
    NONEXISTENT_USER(false, 2200, "존재하지 않는 회원입니다."),

    // company
    DUPLICATED_REGISTRATION_NUM(false, 2300, "이미 등록된 사업자등록번호입니다."),
    NO_AUTHORITY_USER(false, 2301, "해당 회사에 권한이 없는 유저입니다."),



    /**
     * 3000 : Response 오류
     */
    // Common
    RESPONSE_ERROR(false, 3000, "값을 불러오는데 실패하였습니다."),

    // [POST] /users
    DUPLICATED_EMAIL(false, 3013, "중복된 이메일입니다."),
    FAILED_TO_LOGIN(false,3014,"없는 아이디거나 비밀번호가 틀렸습니다."),

    RESULT_ROWS_EMPTY(false,3015,"ROW 결과조회가 없습니다."),
    NONEXISTENT_RESUME(false,3016, "존재하지 않는 이력서입니다."),



    /**
     * 4000 : Database, Server 오류
     */
    DATABASE_ERROR(false, 4000, "데이터베이스 연결에 실패하였습니다."),
    SERVER_ERROR(false, 4001, "서버와의 연결에 실패하였습니다."),

    //[PATCH] /users/{userIdx}
    MODIFY_FAIL_USERNAME(false,4014,"유저네임 수정 실패"),

    PASSWORD_ENCRYPTION_ERROR(false, 4011, "비밀번호 암호화에 실패하였습니다."),
    PASSWORD_DECRYPTION_ERROR(false, 4012, "비밀번호 복호화에 실패하였습니다."),

    // Company
    FAILED_CREATE_RESUME_LIKE(false, 4100, "이력서 좋아요가 등록되지 않았습니다."),
    FAILED_DELETE_RESUME_LIKE(false, 4101, "이력서 좋아요가 취소되지 않았습니다."),
    FAILED_CREATE_COMPANY_OFFER(false, 4102, "입사 제안이 등록되지 않았습니다."),

    // profile
    FAILED_PATCH_SEEK_STATUS(false, 4200, "구직 여부 설정이 변경되지 않았습니다."),
    FAILED_PATCH_BASIC_RESUME(false, 4201, "기본 이력서 설정이 변경되지 않았습니다."),


    /**
     * 5000 : Advisor에 등록되지 않은 오류
     */
    NOT_REGISTRATION_ERROR(false, 5000, "등록되지 않은 에러입니다. 서버 측에 전달해주세요.");

    // 6000 : 필요시 만들어서 쓰세요


    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
