package com.example.demo.src.company;

import com.example.demo.com.exception.BaseException;
import com.example.demo.src.company.model.*;
import com.example.demo.src.company.model.res.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.demo.com.exception.BaseResponseStatus.NO_AUTHORITY_USER;

@Service
@RequiredArgsConstructor
@Transactional
public class CompanyProvider {

    private final CompanyDao companyDao;

    public GetCompanyDTO.ResponseDTO getCompany(Long companyId) {
        return companyDao.getCompany(companyId);
    }

    public Company getCompanyManagement(Long userId, Long companyId) throws Exception{
        // 접속한 사람의 회사인지 확인
        if(!companyDao.getCompanyByuserId(userId, companyId)) {
            throw new BaseException(NO_AUTHORITY_USER);
        }

        return companyDao.getCompanyManagement(companyId);
    }

    public List<GetApplicationsRes> getApplications(Long companyId) {
        return companyDao.getApplications(companyId);
    }

    public GetApplicationsDTO.ResponseDTO getApplication(Long applicationId) {
        return companyDao.getApplication(applicationId);
    }

    public List<GetResumeListDTO.ResponseDTO> getResumes() {
        return companyDao.getResumes();
    }

    public GetResumeDTO.ResponseDTO getResume(Long resumeId) {
        return companyDao.getResume(resumeId);
    }
}
