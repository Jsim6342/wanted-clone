package com.example.demo.src.company;

import com.example.demo.src.company.model.getCompanyDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyProvider {

    private final CompanyDao companyDao;

    public getCompanyDTO.ResponseDTO getCompany(Long companyId) {

        getCompanyDTO.ResponseDTO getCompanyRes = companyDao.getCompany(companyId);
        return getCompanyRes;
    }
}
