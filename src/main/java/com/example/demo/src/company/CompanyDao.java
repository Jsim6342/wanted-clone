package com.example.demo.src.company;

import com.example.demo.config.exception.BaseException;
import com.example.demo.src.company.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

import static com.example.demo.config.response.BaseResponseStatus.NONEXISTENT_RESUME;

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

    public List<GetApplicationsRes> getApplications(Long companyId) {
        //회사 지원서idx SELECT
        String employmentIdxSql = "select employment_idx from Employment where company_idx = ?";
        Long employmentIdxParams = companyId;
        List<Long> employmentIdxList = this.jdbcTemplate.query(employmentIdxSql,
                (rs, rowNum) -> Long.valueOf(rs.getLong("employment_idx")),
                employmentIdxParams);

        if(employmentIdxList.isEmpty()) {
            return new ArrayList<GetApplicationsRes>();
        }

        //지원서 SELECT
        List<GetApplicationsRes> result = new ArrayList<>();
        for (Long employmentIdx : employmentIdxList) {
            result.add(getApplicationsRes(employmentIdx));
        }
        return result;

    }

    public GetApplicationsRes getApplicationsRes(Long employmentIdx) {
        String applicationSql = "select application_idx, application_status, user_name, user_email, user_phone from Application where employment_idx = ?";
        Long applicaitonParams = employmentIdx;
        return this.jdbcTemplate.queryForObject(applicationSql,
                (rs, rowNum) -> new GetApplicationsRes(
                        rs.getLong("application_idx"),
                        rs.getString("application_status"),
                        rs.getString("user_name"),
                        rs.getString("user_email"),
                        rs.getString("user_phone")),
                applicaitonParams);
    }


    public GetApplicationsDTO.ResponseDTO getApplication(Long applicationId) {

        // 지원서 출력 내용 조회
        StringBuffer br = new StringBuffer();
        br.append("SELECT u.user_idx, u.user_name, u.user_email, u.user_phone_number, ");
        br.append("a.application_idx, a.employment_idx, a.recommend, a.application_status, a.updated, ");
        br.append("r.resume_idx, r.title FROM Application a ");
        br.append("INNER JOIN User u ON u.user_idx = a.user_idx ");
        br.append("INNER JOIN Resume r ON r.resume_idx = a.resume_idx ");
        br.append("WHERE a.application_idx = ?");
        String applicationSql = br.toString();

        Long applicaitonParams = applicationId;
        GetApplicationsDTO.applicationDTO applicationDTO = this.jdbcTemplate.queryForObject(applicationSql,
                (rs, rowNum) -> new GetApplicationsDTO.applicationDTO(
                        rs.getLong("user_idx"),
                        rs.getString("user_name"),
                        rs.getString("user_email"),
                        rs.getString("user_phone_number"),
                        rs.getLong("application_idx"),
                        rs.getLong("employment_idx"),
                        rs.getString("recommend"),
                        rs.getString("application_status"),
                        rs.getString("updated"),
                        rs.getLong("resume_idx"),
                        rs.getString("title")),
                applicaitonParams);

        // 첨부파일 SELECT
        String fileSql = "select attached_file_idx, file_name, save_path from Attached_File where application_idx = ?";
        Long fileParams = applicationId;
        List<GetApplicationsDTO.file> fileList = this.jdbcTemplate.query(fileSql,
                (rs, rowNum) -> new GetApplicationsDTO.file(
                        rs.getLong("attached_file_idx"),
                        rs.getString("file_name"),
                        rs.getString("save_path")),
                fileParams);

        // result
        return GetApplicationsDTO.ResponseDTO.builder()
                .applicationDTO(applicationDTO)
                .fileList(fileList)
                .build();
    }

    public void setApplicationStatus(Long applicationId, String status) {
    String sql = "update Application set application_status = ? where application_idx = ?";
    Object[] params = new Object[]{status, applicationId};
    this.jdbcTemplate.update(sql, params);

    }

    public List<GetResumeListDTO.ResponseDTO> getResumes() {

        List<GetResumeListDTO.ResponseDTO> result = new ArrayList<>();

        // 이력서 출력 SELECT
        StringBuffer br = new StringBuffer();
        br.append("SELECT u.basic_resume_idx, r.title, r.user_name, s.major FROM User u ");
        br.append("INNER JOIN Resume r ON r.resume_idx = u.basic_resume_idx ");
        br.append("INNER JOIN School s ON s.resume_idx = r.resume_idx ");
        br.append("WHERE u.seek_status = '현재 구직 중' ");
        String resumeSql = br.toString();

        List<GetResumeListDTO.resumeDTO> resumeList = this.jdbcTemplate.query(resumeSql,
                (rs, rowNum) -> new GetResumeListDTO.resumeDTO(
                        rs.getLong("basic_resume_idx"),
                        rs.getString("title"),
                        rs.getString("user_name"),
                        rs.getString("major"))
        );

        // 스킬 정보 SELECT
        StringBuffer buffer = new StringBuffer();
        buffer.append("SELECT s.skill_name FROM Skill_Res_Map m ");
        buffer.append("INNER JOIN Skill s ON s.skill_num = m.skill_num ");
        buffer.append("WHERE m.resume_idx = ?");
        String skillSql = buffer.toString();

        for (GetResumeListDTO.resumeDTO resume : resumeList) {

            Long skillParam = resume.getResumeIdx();
            List<String> skills = this.jdbcTemplate.query(skillSql,
                    (rs, row) -> new String(
                            rs.getString("skill_name")),
                    skillParam);

            result.add(new GetResumeListDTO.ResponseDTO(resume, skills));

        }

        return result;
    }

    public GetResumeDTO.ResponseDTO getResume(Long resumeId) {

        // 이력서 SELECT
        String resumeSql = "select user_name, user_email, user_phone_number, contents from Resume where resume_idx = ?";
        Long resumeParams = resumeId;
        List<GetResumeDTO.Resume> resumeList = this.jdbcTemplate.query(resumeSql,
                (rs, rowNum) -> new GetResumeDTO.Resume(
                        rs.getString("user_name"),
                        rs.getString("user_email"),
                        rs.getString("user_phone_number"),
                        rs.getString("contents")),
                resumeParams);

        if(resumeList.isEmpty()) {
            new BaseException(NONEXISTENT_RESUME);
        }

        // 학력 SELECT
        String schoolSql = "select school_name, major, subject_contents, entranced, graduated, in_school from School where resume_idx = ?";
        Long schoolParams = resumeId;
        List<GetResumeDTO.School> schoolList = this.jdbcTemplate.query(schoolSql,
                (rs, rowNum) -> new GetResumeDTO.School(
                        rs.getString("school_name"),
                        rs.getString("major"),
                        rs.getString("subject_contents"),
                        rs.getString("entranced"),
                        rs.getString("graduated"),
                        rs.getLong("in_school")),
                schoolParams);
        GetResumeDTO.School school = DataAccessUtils.singleResult(schoolList);

        // 경력 SELECT
        String careerSql = "select company_name, department_name, outcome_name, outcome_start, outcome_end, outcome_contents from Career where resume_idx = ?";
        Long careerParams = resumeId;
        List<GetResumeDTO.Career> careerList = this.jdbcTemplate.query(careerSql,
                (rs, rowNum) -> new GetResumeDTO.Career(
                        rs.getString("company_name"),
                        rs.getString("department_name"),
                        rs.getString("outcome_name"),
                        rs.getString("outcome_start"),
                        rs.getString("outcome_end"),
                        rs.getString("outcome_contents")),
                careerParams);

        // 스킬 SELECT
        StringBuffer buffer = new StringBuffer();
        buffer.append("SELECT s.skill_name FROM Skill_Res_Map m ");
        buffer.append("INNER JOIN Skill s ON s.skill_num = m.skill_num ");
        buffer.append("WHERE m.resume_idx = ?");

        String skillSql = buffer.toString();
        Long skillParams = resumeId;
        List<String> skillList = this.jdbcTemplate.query(skillSql,
                (rs, rowNum) -> new String(
                        rs.getString("skill_name")),
                skillParams);

        // 외국어 + 시험 SELECT
        List<GetResumeDTO.LanguageDTO> languageDTOList = new ArrayList<>();

        String languageSql = "select foreign_language_idx, language, language_level from Foreign_Language where resume_idx = ?";
        Long languageParams = resumeId;
        List<GetResumeDTO.Language> languageList = this.jdbcTemplate.query(languageSql,
                (rs, rowNum) -> new GetResumeDTO.Language(
                        rs.getLong("foreign_language_idx"),
                        rs.getString("language"),
                        rs.getString("language_level")),
                languageParams);

        for (GetResumeDTO.Language language : languageList) {

            String testSql = "select test_name, score, acquisition_date from Language_Test where foreign_language_idx = ?";
            Long testParams = language.getForeignLanguageIdx();
            List<GetResumeDTO.LanguageTest> languageTestList = this.jdbcTemplate.query(testSql,
                    (rs, rowNum) -> new GetResumeDTO.LanguageTest(
                            rs.getString("test_name"),
                            rs.getString("score"),
                            rs.getString("acquisition_date")),
                    testParams);

            languageDTOList.add(new GetResumeDTO.LanguageDTO(language, languageTestList));
        }

        // 포트폴리오 SELECT
        String portfolioSql = "select portfolio_url_1, portfolio_url_2, portfolio_url_3 from Portfolio_URL where resume_idx = ?";
        Long portfolioParams = resumeId;
        List<GetResumeDTO.Portfolio> portfolio = this.jdbcTemplate.query(portfolioSql,
                (rs, rowNum) -> new GetResumeDTO.Portfolio(
                        rs.getString("portfolio_url_1"),
                        rs.getString("portfolio_url_2"),
                        rs.getString("portfolio_url_3")),
                portfolioParams);

        //result build
        return GetResumeDTO.ResponseDTO.builder()
                .resume(resumeList.get(0))
                .schoolList(schoolList)
                .careerList(careerList)
                .skillList(skillList)
                .languageList(languageDTOList)
                .portfolio(portfolio.isEmpty() ? null : portfolio.get(0))
                .build();

    }
}
