package com.example.demo.src.company;

import com.example.demo.src.company.model.GetCompanyRes;
import com.example.demo.src.company.model.PostCompanyReq;
import com.example.demo.src.user.model.GetUserRes;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class CompanyDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public void createCompany(PostCompanyReq req) {
        StringBuffer br = new StringBuffer();
        br.append("insert into Company ");
        br.append("(");
        br.append("user_idx, company_name, company_nation, company_location, company_address, registration_num, ");
        br.append("sales, industry_group, company_size, company_introduce, establishment, company_email, ");
        br.append("company_phone_num, company_url, response");
        br.append(") ");
        br.append("VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        String sql = br.toString();

        Object[] params = new Object[]{
                1L, req.getCompanyName(), req.getCompanyNation(), req.getCompanyLocation(), req.getCompanyAddress(),
                req.getRegistrationNum(), req.getSales(), req.getIndustryGroup(), req.getCompanySize(),
                req.getIntroduce(), req.getEstablishment(), req.getCompanyEmail(), req.getCompanyPhone(),
                req.getCompanyUrl(), req.getResponse()
        };
        this.jdbcTemplate.update(sql, params);
    }

//    public GetCompanyRes getCompany(int companyId) {
//        String getUsersByEmailQuery = "select * from UserInfo where email =?";
//        String getUsersByEmailParams = email;
//        return this.jdbcTemplate.query(getUsersByEmailQuery,
//                (rs, rowNum) -> new GetCompanyRes(
//                        rs.getString("company_name"),
//                        rs.getString("userName"),
//                        rs.getString("ID"),
//                        rs.getString("Email"),
//                        rs.getString("password")),
//                getUsersByEmailParams);
//
//    }
}
