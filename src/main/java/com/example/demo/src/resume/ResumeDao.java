package com.example.demo.src.resume;

import com.example.demo.config.exception.BaseException;
import com.example.demo.src.company.model.GetResumeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

import static com.example.demo.config.response.BaseResponseStatus.NONEXISTENT_RESUME;

@Repository
public class ResumeDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
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
