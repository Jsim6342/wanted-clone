package com.example.demo.src.company;

import com.example.demo.src.company.model.GetCompanyRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyProvider {

    private final CompanyDao companyDao;

//    public GetCompanyRes getCompany(int companyId) {
//
//        GetCompanyRes getCompanyRes = companyDao.getCompany(companyId);
//    }
}
