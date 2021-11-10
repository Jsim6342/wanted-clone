package com.example.demo.src.profile;

import com.example.demo.config.exception.BaseException;
import com.example.demo.src.profile.model.req.SeekStatus;
import com.example.demo.src.profile.model.res.ApplicationDTO;
import com.example.demo.src.profile.model.res.GetProfileDTO;
import com.example.demo.src.profile.model.res.MyWantedDTO;
import com.example.demo.src.profile.model.res.OffersRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

import static com.example.demo.config.response.BaseResponseStatus.*;

@Repository
public class ProfileDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public MyWantedDTO.ResponseDTO getMyWanted(Long userId) {
        // 유저 정보 SELECT
        String userSql = "select user_name, user_email, user_phone_number, point from User where user_idx = ?";
        Long userParams = userId;
        List<MyWantedDTO.User> userList = this.jdbcTemplate.query(userSql,
                (rs, rowNum) -> new MyWantedDTO.User(
                        rs.getString("user_name"),
                        rs.getString("user_email"),
                        rs.getString("user_phone_number"),
                        rs.getLong("point")),
                userParams);
        if(userList.isEmpty()) {
            new BaseException(NONEXISTENT_USER);
        }

        // 관심사 SELECT
        StringBuffer buffer = new StringBuffer();
        buffer.append("SELECT u.interests_name FROM Interests_Map i ");
        buffer.append("INNER JOIN User_Interests u ON u.interests_num = i.interests_num ");
        buffer.append("WHERE i.user_idx = ?");

        String interestSql = buffer.toString();
        Long interestParams = userId;
        List<String> interestList = this.jdbcTemplate.query(interestSql,
                (rs, rowNum) -> new String(
                        rs.getString("interests_name")),
                interestParams);

        // 지원현황 개수 SELECT
        String applicationSql = "select application_status from Application where user_idx = ?";
        Long applicationParams = userId;
        List<String> applicationStatusList = this.jdbcTemplate.query(applicationSql,
                (rs, rowNum) -> new String(
                        rs.getString("application_status")),
                applicationParams);

        MyWantedDTO.ApplicationStatus applicationStatus = new MyWantedDTO.ApplicationStatus();

        if(!applicationStatusList.isEmpty()) {
            for (String status : applicationStatusList) {
                if(status.equals("지원 완료")) { applicationStatus.addComplete(); continue;}
                if(status.equals("서류 통과")) { applicationStatus.addComplete(); continue;}
                if(status.equals("최종 합격")) { applicationStatus.addComplete(); continue;}
                if(status.equals("불합격")) { applicationStatus.addComplete(); continue;}
            }
        }

        // 제안 개수 SELECT
        int resumeWanted = 0;
        int resumeOpen = 0;
        int offerCount = 0;

        // 내가 가진 이력서 idx 조회
        String resumeSql = "select resume_idx from Resume where user_idx = ?";
        Long resumeParams = userId;
        List<Long> resumeIdList = this.jdbcTemplate.query(resumeSql,
                (rs, rowNum) -> rs.getLong("resume_idx"),
                resumeParams);

        // 이력서 원해요, 열람 개수 count
        for (Long resumeId : resumeIdList) {
            String resumeWantSql = "select count(*) as count from Resume_Want where resume_idx = ?";
            resumeWanted += this.jdbcTemplate.queryForObject(resumeWantSql, int.class, resumeId);

            String resumeOpneSql = "select open_count from Resume where resume_idx = ?";
            resumeOpen += this.jdbcTemplate.queryForObject(resumeOpneSql, int.class, resumeId);
        }

        String offertSql = "select count(*) as count from Job_Offer where user_idx = ?";
        offerCount += this.jdbcTemplate.queryForObject(offertSql, int.class, userId);

        MyWantedDTO.OfferStatus offerStatus = MyWantedDTO.OfferStatus.builder()
                .resumeWanted(resumeWanted)
                .resumeOpen(resumeOpen)
                .offerCount(offerCount)
                .build();


        // 추천 채용 SELECT
        String specializedSql = "select field from Specialized where user_idx = ?";
        List<String> fieldList = this.jdbcTemplate.query(specializedSql,
                (rs, rowNum) -> rs.getString("field"),
                userId);

        List<MyWantedDTO.RecommendEmployment> recommendEmploymentList = new ArrayList<>();

        if(!fieldList.isEmpty()) {
            String sql = "select employment_idx, emp_title, company_name, company_location, (rec_reward + vol_reward) as reward" +
                    "from Employment where job_group = ?";
            String param = fieldList.get(0);
            recommendEmploymentList = this.jdbcTemplate.query(sql,
                    (rs, rowNum) -> new MyWantedDTO.RecommendEmployment(
                            rs.getLong("employment_idx"),
                            rs.getString("emp_title"),
                            rs.getString("company_name"),
                            rs.getString("user_phone_number"),
                            rs.getLong("reward")),
                    userId);
        }


        // 좋아요 채용 SELECT
        StringBuffer buffer2 = new StringBuffer();
        buffer2.append("select e.employment_idx, e.emp_title, e.company_name, e.company_location from Emp_Like l ");
        buffer2.append("INNER JOIN Employment e on l.employment_idx = e.employment_idx ");
        buffer2.append("where user_idx = ?");

        String likeEmploymentSql = buffer2.toString();
        Long likeEmploymentParams = userId;
        List<MyWantedDTO.LikeEmployment> likeEmploymentList = this.jdbcTemplate.query(likeEmploymentSql,
                (rs, rowNum) -> new MyWantedDTO.LikeEmployment(
                        rs.getLong("employment_idx"),
                        rs.getString("emp_title"),
                        rs.getString("company_name"),
                        rs.getString("company_location")),
                likeEmploymentParams);

        // 북마크 채용 SELECT
        StringBuffer buffer3 = new StringBuffer();
        buffer3.append("select e.employment_idx, e.emp_title, e.company_name, e.company_location from Emp_Bookmark b ");
        buffer3.append("INNER JOIN Employment e on b.employment_idx = e.employment_idx ");
        buffer3.append("where user_idx = ?");

        String bookmarkEmploymentSql = buffer3.toString();
        Long bookmarkEmploymentParams = userId;
        List<MyWantedDTO.BookmarkEmployment> bookmarkEmploymentList = this.jdbcTemplate.query(bookmarkEmploymentSql,
                (rs, rowNum) -> new MyWantedDTO.BookmarkEmployment(
                        rs.getLong("employment_idx"),
                        rs.getString("emp_title"),
                        rs.getString("company_name"),
                        rs.getString("company_location")),
                bookmarkEmploymentParams);

        // build & return
        return MyWantedDTO.ResponseDTO.builder()
                .user(userList.get(0))
                .interestList(interestList)
                .applicationStatus(applicationStatus)
                .offerStatus(offerStatus)
                .recommendEmploymentList(recommendEmploymentList)
                .likeEmploymentList(likeEmploymentList)
                .bookmarkEmploymentList(bookmarkEmploymentList)
                .build();

    }

    public GetProfileDTO.ResponseDTO getProfile(Long userId) {
        // 유저 정보 SELECT
        String userSql = "select user_name, user_email, user_phone_number, point, basic_resume_idx from User where user_idx = ?";
        Long userParams = userId;
        List<GetProfileDTO.User> user = this.jdbcTemplate.query(userSql,
                (rs, rowNum) -> new GetProfileDTO.User(
                        rs.getString("user_name"),
                        rs.getString("user_email"),
                        rs.getString("user_phone_number"),
                        rs.getLong("point"),
                        rs.getLong("basic_resume_idx")),
                userParams);
        if(user.isEmpty()) {
            new BaseException(NONEXISTENT_USER);
        }

        // 기본 이력서 정보 SELECT
        StringBuffer br = new StringBuffer();
        br.append("select r.resume_idx, r.title, s.school_name, s.major, c.company_name, c.department_name, r.contents from Resume r ");
        br.append("INNER join ( ");
        br.append("select resume_idx, school_name, major from School where resume_idx = ? ");
        br.append(") s ON r.resume_idx = s.resume_idx ");
        br.append("INNER join ( ");
        br.append("select resume_idx, company_name, department_name from Career where resume_idx = ? ");
        br.append(") c ON r.resume_idx = c.resume_idx");


        String resumeSql = br.toString();
        Long resumeParams = user.get(0).getBasicResumeId();
        List<GetProfileDTO.BasicResume> basicResume = this.jdbcTemplate.query(resumeSql,
                (rs, rowNum) -> new GetProfileDTO.BasicResume(
                        rs.getLong("resume_idx"),
                        rs.getString("title"),
                        rs.getString("school_name"),
                        rs.getString("major"),
                        rs.getString("company_name"),
                        rs.getString("department_name"),
                        rs.getString("contents")),
                resumeParams, resumeParams);


        // 전문분야 SELECT
        String specializedSql = "select specialized_idx, field, job_group_1, job_group_2, job_group_3, career from Specialized where user_idx = ?";
        Long specializedParams = userId;
        List<GetProfileDTO.Specialized> specialized = this.jdbcTemplate.query(specializedSql,
                (rs, rowNum) -> new GetProfileDTO.Specialized(
                        rs.getLong("specialized_idx"),
                        rs.getString("field"),
                        rs.getString("job_group_1"),
                        rs.getString("job_group_2"),
                        rs.getString("job_group_3"),
                        rs.getString("career")),
                specializedParams);

        List<String> skillList = new ArrayList<>();
        if(!specialized.isEmpty()) {
            StringBuffer buffer = new StringBuffer();
            buffer.append("SELECT s.skill_name FROM Skill_Spe_Map m ");
            buffer.append("INNER JOIN Skill s ON s.skill_num = m.skill_num ");
            buffer.append("WHERE m.specialized_idx = ?");

            String skillSql = buffer.toString();
            Long skillParams = specialized.get(0).getSpecialized_idx();
            skillList = this.jdbcTemplate.query(skillSql,
                    (rs, rowNum) -> new String(
                            rs.getString("skill_name")),
                    skillParams);
        }

        GetProfileDTO.SpecializedDTO specializedDTO = GetProfileDTO.SpecializedDTO.builder()
                .specialized(specialized.isEmpty() ? new GetProfileDTO.Specialized() : specialized.get(0))
                .skillList(skillList)
                .build();

        // build & return
        return GetProfileDTO.ResponseDTO.builder()
                .user(user.get(0))
                .basicResume(basicResume.isEmpty() ? new GetProfileDTO.BasicResume() : basicResume.get(0))
                .specializedDTO(specializedDTO)
                .build();


    }

    public void modifySeekStatus(Long userId, SeekStatus seekStatus) {
        String sql = "update User set seek_status = ? where user_idx = ?";
        Object[] params = new Object[]{seekStatus.getSeekStatus(), userId};
        if(this.jdbcTemplate.update(sql, params)==0) {
            new BaseException(FAILED_PATCH_SEEK_STATUS);
        }
    }

    public void modifyBasicResume(Long userId, Long resumeId) {
        String sql = "update User set basic_resume_idx = ? where user_idx = ?";
        Object[] params = new Object[]{resumeId, userId};
        if(this.jdbcTemplate.update(sql, params)==0) {
            new BaseException(FAILED_PATCH_BASIC_RESUME);
        }
    }

    public List<ApplicationDTO.ResponseDTO> getApplicationWriting(Long userId) {

        // 채용 정보 SELECT
        StringBuffer br = new StringBuffer();
        br.append("select a.application_idx, e.employment_idx, e.company_name, e.emp_title, e.updated, a.application_status ");
        br.append("from Application a ");
        br.append("INNER JOIN Employment e on a.employment_idx = e.employment_idx ");
        br.append("where a.user_idx = ? AND a.application_status = '작성중' ");


        String employmentSql = br.toString();
        Long employmentParams = userId;
        List<ApplicationDTO.Employment> employmentList = this.jdbcTemplate.query(employmentSql,
                (rs, rowNum) -> new ApplicationDTO.Employment(
                        rs.getLong("application_idx"),
                        rs.getLong("employment_idx"),
                        rs.getString("company_name"),
                        rs.getString("emp_title"),
                        rs.getString("updated"),
                        rs.getString("application_status")),
                employmentParams);

        // 회사 이미지 SELECT & build
        List<ApplicationDTO.ResponseDTO> result = new ArrayList<>();
        ApplicationDTO.ResponseDTO responseDTO = new ApplicationDTO.ResponseDTO();

        if(!employmentList.isEmpty()) {
            for (ApplicationDTO.Employment employment : employmentList) {

                StringBuffer buffer = new StringBuffer();
                buffer.append("select ci.company_img_1 from Employment e ");
                buffer.append("INNER JOIN Company c on e.company_idx = c.company_idx ");
                buffer.append("INNER JOIN Company_Img ci on c.company_idx = ci.company_idx ");
                buffer.append("where e.employment_idx = ? ");


                String imageSql = buffer.toString();
                Long imageParams = userId;
                List<String> imageList = this.jdbcTemplate.query(imageSql,
                        (rs, rowNum) -> rs.getString("company_img_1"),
                        imageParams);

                responseDTO.setImageUrl(imageList.isEmpty() ? "" : imageList.get(0));
                responseDTO.setEmployment(employment);
                result.add(responseDTO);

            }
        }

        return result;

    }

    public List<ApplicationDTO.ResponseDTO> getApplications(Long userId) {

        // 채용 정보 SELECT
        StringBuffer br = new StringBuffer();
        br.append("select a.application_idx, e.employment_idx, e.company_name, e.emp_title, e.updated, a.application_status ");
        br.append("from Application a ");
        br.append("INNER JOIN Employment e on a.employment_idx = e.employment_idx ");
        br.append("where a.user_idx = ? AND a.application_status != '작성중' ");


        String employmentSql = br.toString();
        Long employmentParams = userId;
        List<ApplicationDTO.Employment> employmentList = this.jdbcTemplate.query(employmentSql,
                (rs, rowNum) -> new ApplicationDTO.Employment(
                        rs.getLong("application_idx"),
                        rs.getLong("employment_idx"),
                        rs.getString("company_name"),
                        rs.getString("emp_title"),
                        rs.getString("updated"),
                        rs.getString("application_status")),
                employmentParams);

        // 회사 이미지 SELECT & build
        List<ApplicationDTO.ResponseDTO> result = new ArrayList<>();
        ApplicationDTO.ResponseDTO responseDTO = new ApplicationDTO.ResponseDTO();

        if(!employmentList.isEmpty()) {
            for (ApplicationDTO.Employment employment : employmentList) {

                StringBuffer buffer = new StringBuffer();
                buffer.append("select ci.company_img_1 from Employment e ");
                buffer.append("INNER JOIN Company c on e.company_idx = c.company_idx ");
                buffer.append("INNER JOIN Company_Img ci on c.company_idx = ci.company_idx ");
                buffer.append("where e.employment_idx = ? ");


                String imageSql = buffer.toString();
                Long imageParams = userId;
                List<String> imageList = this.jdbcTemplate.query(imageSql,
                        (rs, rowNum) -> rs.getString("company_img_1"),
                        imageParams);

                responseDTO.setImageUrl(imageList.isEmpty() ? "" : imageList.get(0));
                responseDTO.setEmployment(employment);
                result.add(responseDTO);

            }
        }

        return result;
    }

    public List<OffersRes> getAllOffers(Long userId) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("select w.resume_want_idx, c.company_idx, c.company_name, w.resume_want_status, w.updated from Resume_Want w ");
        buffer.append("INNER JOIN Company c on w.company_idx = c.company_idx ");
        buffer.append("INNER JOIN Resume r on w.resume_idx = r.resume_idx ");
        buffer.append("where r.user_idx = ? ");
        buffer.append("UNION ALL ");
        buffer.append("select o.open_profile_idx, c.company_idx, c.company_name, o.open_profile_status, o.updated from Open_Profile o ");
        buffer.append("INNER JOIN Company c on o.company_idx = c.company_idx ");
        buffer.append("INNER JOIN Resume r on o.resume_idx = r.resume_idx ");
        buffer.append("where r.user_idx = ? ");
        buffer.append("UNION ALL ");
        buffer.append("select j.job_offer_idx, c.company_idx, c.company_name, j.job_offer_status, j.updated from Job_Offer j ");
        buffer.append("INNER JOIN Company c on j.company_idx = c.company_idx ");
        buffer.append("where j.user_idx = ? ");


        String offersSql = buffer.toString();
        Long offersParam = userId;
        return this.jdbcTemplate.query(offersSql,
                (rs, rowNum) -> new OffersRes(
                        rs.getLong("resume_want_idx"),
                        rs.getLong("company_idx"),
                        rs.getString("company_name"),
                        rs.getString("resume_want_status"),
                        rs.getString("updated")),
                userId, userId, userId);




    }

    public List<OffersRes> getLikesOffers(Long userId) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("select w.resume_want_idx, c.company_idx, c.company_name, w.resume_want_status, w.updated from Resume_Want w ");
        buffer.append("INNER JOIN Company c on w.company_idx = c.company_idx ");
        buffer.append("INNER JOIN Resume r on w.resume_idx = r.resume_idx ");
        buffer.append("where r.user_idx = ? ");

        String offersSql = buffer.toString();
        Long offersParam = userId;
        return this.jdbcTemplate.query(offersSql,
                (rs, rowNum) -> new OffersRes(
                        rs.getLong("resume_want_idx"),
                        rs.getLong("company_idx"),
                        rs.getString("company_name"),
                        rs.getString("resume_want_status"),
                        rs.getString("updated")),
                offersParam);

    }

    public List<OffersRes> getOpensOffers(Long userId) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("select o.open_profile_idx, c.company_idx, c.company_name, o.open_profile_status, o.updated from Open_Profile o ");
        buffer.append("INNER JOIN Company c on o.company_idx = c.company_idx ");
        buffer.append("INNER JOIN Resume r on o.resume_idx = r.resume_idx ");
        buffer.append("where r.user_idx = ? ");

        String offersSql = buffer.toString();
        Long offersParam = userId;
        return this.jdbcTemplate.query(offersSql,
                (rs, rowNum) -> new OffersRes(
                        rs.getLong("resume_want_idx"),
                        rs.getLong("company_idx"),
                        rs.getString("company_name"),
                        rs.getString("resume_want_status"),
                        rs.getString("updated")),
                offersParam);
    }

    public List<OffersRes> getOffers(Long userId) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("select j.job_offer_idx, c.company_idx, c.company_name, j.job_offer_status, j.updated from Job_Offer j ");
        buffer.append("INNER JOIN Company c on j.company_idx = c.company_idx ");
        buffer.append("where j.user_idx = ?; ");

        String offersSql = buffer.toString();
        Long offersParam = userId;
        return this.jdbcTemplate.query(offersSql,
                (rs, rowNum) -> new OffersRes(
                        rs.getLong("resume_want_idx"),
                        rs.getLong("company_idx"),
                        rs.getString("company_name"),
                        rs.getString("resume_want_status"),
                        rs.getString("updated")),
                offersParam);

    }
}
