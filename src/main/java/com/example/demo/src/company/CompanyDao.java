package com.example.demo.src.company;

import com.example.demo.src.company.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

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

    public GetCompanyDTO.ResponseDTO getCompany(Long companyId) {
        // 회사 채용정보 SELECT
        String employmentSql = "select employment_idx, emp_title, rec_reward, vol_reward, emp_deadline from Employment where company_idx = ?";
        Long employmentParams = companyId;
        List<GetCompanyDTO.Employments> employmentList = this.jdbcTemplate.query(employmentSql,
                (rs, rowNum) -> new GetCompanyDTO.Employments(
                        rs.getLong("employment_idx"),
                        rs.getString("emp_title"),
                        rs.getLong("rec_reward"),
                        rs.getLong("vol_reward"),
                        rs.getString("emp_deadline")),
                employmentParams);

        // 회사 태그 SELECT
        StringBuffer br = new StringBuffer();
        br.append("SELECT k.keyword_name FROM Com_Key_Map c ");
        br.append("INNER JOIN Keyword k ON c.keyword_num = k.keyword_num ");
        br.append("WHERE c.company_idx = ?");
        String tagSql = br.toString();
        Long tagParams = companyId;
        List<String> keywordList = this.jdbcTemplate.query(tagSql,
                (rs, rowNum) -> new String(
                        rs.getString("keyword_name")),
                tagParams);

        // 회사 이미지 SELECT
        String imageSql = "select company_img_1, company_img_2, company_img_3, company_img_4, company_img_5 from Company_Img where company_idx = ?";
        Long imageParams = companyId;
        GetCompanyDTO.Images images = this.jdbcTemplate.queryForObject(imageSql,
                (rs, rowNum) -> new GetCompanyDTO.Images(
                        rs.getString("company_img_1"),
                        rs.getString("company_img_2"),
                        rs.getString("company_img_3"),
                        rs.getString("company_img_4"),
                        rs.getString("company_img_5")),
                imageParams);

        // 회사 정보 SELECT
        String companySql = "select company_name, company_introduce from Company where company_idx = ?";
        Long companyParams = companyId;
        GetCompanyDTO.Company company = this.jdbcTemplate.queryForObject(companySql,
                (rs, rowNum) -> new GetCompanyDTO.Company(
                        rs.getString("company_name"),
                        rs.getString("company_introduce")),
                companyParams);

        GetCompanyDTO.ResponseDTO responseDTO = new GetCompanyDTO.ResponseDTO();
        responseDTO.setCompanyName(company.getCompanyName());
        responseDTO.setCompanyIntroduce(company.getIntroduce());
        responseDTO.setEmploymentList(employmentList);
        responseDTO.setImageUrls(images);
        responseDTO.setTags(keywordList);

        return responseDTO;
    }

    public void modifyCompany(Long companyId, PatchCompanyReq req) {
        StringBuffer br = new StringBuffer();
        br.append("update Company set ");
        br.append("company_name = ?, company_nation = ?, company_location = ?, company_address = ?, ");
        br.append("sales = ?, industry_group = ?, company_size = ?, company_introduce = ?, ");
        br.append("company_email = ?, company_phone_num = ?, company_url = ? ");
        br.append("where company_idx = ?");
        String sql = br.toString();

        Object[] params = new Object[]{
                req.getCompanyName(), req.getCompanyNation(), req.getCompanyLocation(), req.getCompanyAddress(),
                req.getSales(), req.getIndustryGroup(), req.getCompanySize(),req.getIntroduce(),
                req.getCompanyEmail(), req.getCompanyPhone(), req.getCompanyUrl(), companyId
        };
        this.jdbcTemplate.update(sql, params);
    }

    public Company getCompanyManagement(Long companyId) {
        String sql = "select * from Company where company_idx = ?";

        Long params = companyId;
        return this.jdbcTemplate.queryForObject(sql,
                (rs, rowNum) -> new Company(
                        rs.getLong("company_idx"),
                        rs.getLong("user_idx"),
                        rs.getString("company_name"),
                        rs.getString("company_nation"),
                        rs.getString("company_location"),
                        rs.getString("company_address"),
                        rs.getString("registration_num"),
                        rs.getString("sales"),
                        rs.getString("industry_group"),
                        rs.getLong("company_size"),
                        rs.getString("company_introduce"),
                        rs.getString("establishment"),
                        rs.getString("company_email"),
                        rs.getString("company_phone_num"),
                        rs.getString("company_url"),
                        rs.getString("response"),
                        rs.getString("created"),
                        rs.getString("updated"),
                        rs.getString("status")),
                params);
    }
}
