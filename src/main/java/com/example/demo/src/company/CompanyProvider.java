package com.example.demo.src.company;

import com.example.demo.src.company.model.Company;
import com.example.demo.src.company.model.GetCompanyDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyProvider {

    private final CompanyDao companyDao;

    public GetCompanyDTO.ResponseDTO getCompany(Long companyId) {

        GetCompanyDTO.ResponseDTO getCompanyRes = companyDao.getCompany(companyId);
        return getCompanyRes;
    }

    public Company getCompanyManagement(Long companyId) {
        Company company = companyDao.getCompanyManagement(companyId);
        return company;
    }
}
