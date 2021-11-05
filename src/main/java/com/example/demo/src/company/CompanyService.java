package com.example.demo.src.company;

import com.example.demo.src.company.model.PatchCompanyReq;
import com.example.demo.src.company.model.PostCompanyReq;
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
}
