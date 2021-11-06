package com.example.demo.src.employment;

import com.example.demo.src.employment.model.*;
import com.example.demo.src.user.model.GetUserRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class EmploymentDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Long createEmployment(PostEmploymentReq postEmploymentReq){
        String createEmploymentQuery = "insert into Employment (company_idx, emp_title, company_name, company_location, rec_reward, vol_reward, emp_contents, emp_deadline, work_location) values(?,?,?,?,?,?,?,?,?)";
        Object[] createEmploymentParams = new Object[]{
                postEmploymentReq.getCompanyIdx(),
                postEmploymentReq.getEmpTitle(),
                postEmploymentReq.getCompanyName(),
                postEmploymentReq.getCompanyLocation(),
                postEmploymentReq.getRecReward(),
                postEmploymentReq.getVolReward(),
                postEmploymentReq.getEmpContents(),
                postEmploymentReq.getEmpDeadline(),
                postEmploymentReq.getWorkLocation()};
        this.jdbcTemplate.update(createEmploymentQuery,createEmploymentParams);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery,Long.class);
    }

    public Long employmentLiked(Long employmentIdx, Long userIdxByJwt){
        String employmentLikedQuery = "insert into Emp_Like (user_idx, employment_idx) values(?,?)";
        Object[] employmentLikeParams = new Object[]{userIdxByJwt, employmentIdx};
        this.jdbcTemplate.update(employmentLikedQuery, employmentLikeParams);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery,Long.class);
    }

    public Long employmentBookmark(Long employmentIdx, Long userIdxByJwt){
        String employmentBookmarkQuery = "insert into Emp_Bookmark (user_idx, employment_idx) values(?,?)";
        Object[] employmentBookmarkParams = new Object[]{userIdxByJwt, employmentIdx};
        this.jdbcTemplate.update(employmentBookmarkQuery, employmentBookmarkParams);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery,Long.class);
    }



    public GetEmploymentRes getEmploymentByEmploymentIdx(Long employmentIdx){
        String getEmploymentByEmploymentIdxQuery = "select * from Employment where employment_idx = ?";
        Long getEmploymentByEmploymentIdxParams = employmentIdx;
        return this.jdbcTemplate.queryForObject(getEmploymentByEmploymentIdxQuery,
                (rs, rowNum) -> new GetEmploymentRes(
                        rs.getLong("employment_idx"),
                        rs.getLong("company_idx"),
                        rs.getString("emp_title"),
                        rs.getString("company_name"),
                        rs.getString("company_location"),
                        rs.getLong("rec_reward"),
                        rs.getLong("vol_reward"),
                        rs.getLong("emp_liked"),
                        rs.getLong("career"),
                        rs.getString("emp_contents"),
                        rs.getString("emp_deadline"),
                        rs.getString("work_location"),
                        rs.getDate("created"),
                        rs.getDate("updated"),
                        rs.getString("status")),
                getEmploymentByEmploymentIdxParams);
    }

    public List<GetEmploymentPageRes> getEmploymentPage(String tag, String location, Long year){
        String getEmploymentPageQuery = "select Company_Img.company_img_1, Employment.emp_title, Company.company_name ,Employment.company_location, Employment.rec_reward + Employment.vol_reward as '채용보상금'\n" +
                "from Employment inner join Company on Employment.company_idx = Company.company_idx\n" +
                "inner join Company_Img on Company.company_idx = Company_Img.company_idx\n" +
                "inner join Com_Key_Map on Company.company_idx = Com_Key_Map.company_idx\n" +
                "inner join Keyword on Com_Key_Map.keyword_num = Keyword.keyword_num\n" +
                "where Keyword.keyword_name = ? and Employment.company_location = ? and Employment.career = ?";
        String getEmploymentPageParam1 = tag;
        String getEmploymentPageParam2 = location;
        Long getEmploymentPageParam3 = year;
        return this.jdbcTemplate.query(getEmploymentPageQuery,
                (rs, rowNum) -> new GetEmploymentPageRes(
                        rs.getString("company_img_1"),
                        rs.getString("emp_title"),
                        rs.getString("company_name"),
                        rs.getString("company_location"),
                        rs.getLong("채용보상금")),
                getEmploymentPageParam1, getEmploymentPageParam2, getEmploymentPageParam3);
    }
}
