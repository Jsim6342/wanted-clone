package com.example.demo.src.company;

import com.example.demo.src.company.model.req.PatchCompanyReq;
import com.example.demo.src.company.model.req.PostCompanyReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyDao companyDao;

    public void createCompany(PostCompanyReq postCompanyReq) {
        companyDao.createCompany(postCompanyReq);
    }

    public void modifyCompany(Long companyId, PatchCompanyReq postCompanyReq) {
        companyDao.modifyCompany(companyId, postCompanyReq);
    }

    public void setApplicationStatus(Long applicationId, String status) {
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

    public void deleteCompany(Long companyId) {
        companyDao.deleteCompany(companyId);
    }
}
