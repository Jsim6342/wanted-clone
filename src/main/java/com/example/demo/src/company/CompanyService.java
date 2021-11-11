package com.example.demo.src.company;

import com.example.demo.com.exception.BaseException;
import com.example.demo.src.company.model.req.ApplicationStatus;
import com.example.demo.src.company.model.req.PatchCompanyReq;
import com.example.demo.src.company.model.req.PostCompanyReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.demo.com.exception.BaseResponseStatus.DUPLICATED_REGISTRATION_NUM;
import static com.example.demo.com.exception.BaseResponseStatus.NO_AUTHORITY_USER;

@Service
@RequiredArgsConstructor
@Transactional
public class CompanyService {

    private final CompanyDao companyDao;

    public void createCompany(Long userId, PostCompanyReq postCompanyReq) throws Exception {

        // 일치하는 사업자 번호 조회
        if(!companyDao.getRegistrationNum(postCompanyReq.getRegistrationNum())) {
            throw new BaseException(DUPLICATED_REGISTRATION_NUM);
        }

        companyDao.createCompany(userId, postCompanyReq);
    }

    public void modifyCompany(Long userId, Long companyId, PatchCompanyReq postCompanyReq) throws Exception {

        // 접속한 사람의 회사인지 확인
        if(!companyDao.getCompanyByuserId(userId, companyId)) {
            throw new BaseException(NO_AUTHORITY_USER);
        }

        companyDao.modifyCompany(companyId, postCompanyReq);
    }

    public void setApplicationStatus(Long applicationId, ApplicationStatus status) {
        companyDao.setApplicationStatus(applicationId, status);
    }

    public void createResumeLike(Long companyId, Long resumeId) {
        companyDao.createResumeLike(companyId, resumeId);
    }

    public void deleteResumeLike(Long companyId, Long resumeId) {
        companyDao.deleteResumeLike(companyId, resumeId);
    }

    public void createOffer(Long companyId, Long userId) {

        companyDao.createOffer(companyId, userId);
    }

    public void deleteCompany(Long userId, Long companyId) throws Exception{

        // 접속한 사람의 회사인지 확인
        if(!companyDao.getCompanyByuserId(userId, companyId)) {
            throw new BaseException(NO_AUTHORITY_USER);
        }

        companyDao.deleteCompany(companyId);
    }
}
