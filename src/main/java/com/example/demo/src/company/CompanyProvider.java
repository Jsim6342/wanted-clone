package com.example.demo.src.company;

import com.example.demo.src.company.model.Company;
import com.example.demo.src.company.model.GetApplicationsDTO;
import com.example.demo.src.company.model.GetApplicationsRes;
import com.example.demo.src.company.model.GetCompanyDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyProvider {

    private final CompanyDao companyDao;

    public GetCompanyDTO.ResponseDTO getCompany(Long companyId) {
        return companyDao.getCompany(companyId);
    }

    public Company getCompanyManagement(Long companyId) {
        return companyDao.getCompanyManagement(companyId);
    }

    public List<GetApplicationsRes> getApplications(Long companyId) {
        return companyDao.getApplications(companyId);
    }

    public GetApplicationsDTO.ResponseDTO getApplication(Long applicationId) {
        return companyDao.getApplication(applicationId);
    }
}
