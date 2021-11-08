package com.example.demo.src.resume;

import com.example.demo.config.exception.BaseException;
import com.example.demo.src.company.model.GetResumeDTO;
import com.example.demo.src.resume.model.PostResumeReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static com.example.demo.config.response.BaseResponseStatus.NONEXISTENT_RESUME;

@Slf4j
@Repository
public class ResumeDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Long createResume(PostResumeReq postResumeReq, Long userIdx){
        String createResumeQuery = "insert into Resume (user_idx, user_name, user_email, user_phone_number, contents) values(?,?,?,?,?)";
        KeyHolder mainKeyHolder = new GeneratedKeyHolder();
        this.jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(createResumeQuery, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, userIdx);
            ps.setString(2, postResumeReq.getUserName());
            ps.setString(3, postResumeReq.getUserEmail());
            ps.setString(4, postResumeReq.getUserPhoneNumber());
            ps.setString(5, postResumeReq.getContents());
            return ps;},
                mainKeyHolder);

        String createResumeCareerQuery = "insert into Career (resume_idx, company_name, department_name, outcome_name, outcome_start, outcome_end, outcome_contents) values(?,?,?,?,?,?,?)";
        KeyHolder keyHolder1 = new GeneratedKeyHolder();
        for (int i = 0; i < postResumeReq.getCareerList().size(); i++) {
            int finalI = i;
            this.jdbcTemplate.update(connection -> {
                        PreparedStatement ps = connection.prepareStatement(createResumeCareerQuery, Statement.RETURN_GENERATED_KEYS);
                        ps.setLong(1, mainKeyHolder.getKey().longValue());
                        ps.setString(2, postResumeReq.getCareerList().get(finalI).getCompanyName());
                        ps.setString(3, postResumeReq.getCareerList().get(finalI).getDepartmentName());
                        ps.setString(4, postResumeReq.getCareerList().get(finalI).getOutcomeName());
                        ps.setString(5, postResumeReq.getCareerList().get(finalI).getOutcomeStart());
                        ps.setString(6, postResumeReq.getCareerList().get(finalI).getOutcomeEnd());
                        ps.setString(7, postResumeReq.getCareerList().get(finalI).getOutcomeContents());
                        return ps;},
                    keyHolder1);
        }


        String createResumeSchoolQuery = "insert into School (resume_idx, school_name, major, subject_contents, entranced, graduated, in_school) values(?,?,?,?,?,?,?)";
        KeyHolder keyHolder2 = new GeneratedKeyHolder();
        for (int i = 0; i < postResumeReq.getSchoolList().size(); i++) {
            int finalI = i;
            this.jdbcTemplate.update(connection -> {
                        PreparedStatement ps = connection.prepareStatement(createResumeSchoolQuery, Statement.RETURN_GENERATED_KEYS);
                        ps.setLong(1, mainKeyHolder.getKey().longValue());
                        ps.setString(2, postResumeReq.getSchoolList().get(finalI).getSchoolName());
                        ps.setString(3, postResumeReq.getSchoolList().get(finalI).getMajor());
                        ps.setString(4, postResumeReq.getSchoolList().get(finalI).getSubjectContents());
                        ps.setString(5, postResumeReq.getSchoolList().get(finalI).getEntranced());
                        ps.setString(6, postResumeReq.getSchoolList().get(finalI).getGraduated());
                        ps.setLong(7, postResumeReq.getSchoolList().get(finalI).getInSchool());
                        return ps;},
                    keyHolder2);
        }

        String createResumeForeignLanguageQuery = "insert into Foreign_Language (resume_idx, language, language_level) values(?,?,?)";
        KeyHolder keyHolder3 = new GeneratedKeyHolder();
        for (int i = 0; i < postResumeReq.getLanguageList().size(); i++) {
            int finalI = i;
            this.jdbcTemplate.update(connection -> {
                        PreparedStatement ps = connection.prepareStatement(createResumeForeignLanguageQuery, Statement.RETURN_GENERATED_KEYS);
                        ps.setLong(1, mainKeyHolder.getKey().longValue());
                        ps.setString(2, postResumeReq.getLanguageList().get(finalI).getLanguage());
                        ps.setString(3, postResumeReq.getLanguageList().get(finalI).getLanguageLevel());
                        return ps;},
                    keyHolder3);
        }

        String createResumePortfolioQuery = "insert into Portfolio_URL (resume_idx, portfolio_url_1, portfolio_url_2, portfolio_url_3) values(?,?,?,?)";
        KeyHolder keyHolder4 = new GeneratedKeyHolder();
        for (int i = 0; i < postResumeReq.getPortfoliosList().size(); i++) {
            int finalI = i;
            this.jdbcTemplate.update(connection -> {
                        PreparedStatement ps = connection.prepareStatement(createResumePortfolioQuery, Statement.RETURN_GENERATED_KEYS);
                        ps.setLong(1, mainKeyHolder.getKey().longValue());
                        ps.setString(2, postResumeReq.getPortfoliosList().get(finalI).getPortfolioUrl1());
                        ps.setString(3, postResumeReq.getPortfoliosList().get(finalI).getPortfolioUrl2());
                        ps.setString(4, postResumeReq.getPortfoliosList().get(finalI).getPortfolioUrl3());
                        return ps;},
                    keyHolder4);
        }

        Long resumeIdx =  mainKeyHolder.getKey().longValue();
        return resumeIdx;
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
                .portfolio(portfolio.isEmpty() ? new GetResumeDTO.Portfolio() : portfolio.get(0))
                .build();

    }
}
