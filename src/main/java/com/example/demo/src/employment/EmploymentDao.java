package com.example.demo.src.employment;

import com.example.demo.src.employment.model.*;
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
        String createEmploymentQuery = "insert into Employment (company_idx, emp_title, job_group ,company_name, company_location, rec_reward, vol_reward, emp_contents, emp_deadline, work_location) values(?,?,?,?,?,?,?,?,?,?)";
        Object[] createEmploymentParams = new Object[]{
                postEmploymentReq.getCompanyIdx(),
                postEmploymentReq.getEmpTitle(),
                postEmploymentReq.getJobGroup(),
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

    public Long employmentLikedDelete(Long employmentIdx, Long userIdxByJwt){
        String employmentLikedDeleteQuery = "delete from Emp_Like where employment_idx = ? and user_idx = ?";
        Object[] employmentLikedDeleteParams = new Object[]{employmentIdx, userIdxByJwt};
        this.jdbcTemplate.update(employmentLikedDeleteQuery, employmentLikedDeleteParams);

        Long deletedEmploymentIdx = employmentIdx;
        return deletedEmploymentIdx;
    }

    public Long employmentBookmark(Long employmentIdx, Long userIdxByJwt){
        String employmentBookmarkQuery = "insert into Emp_Bookmark (user_idx, employment_idx) values(?,?)";
        Object[] employmentBookmarkParams = new Object[]{userIdxByJwt, employmentIdx};
        this.jdbcTemplate.update(employmentBookmarkQuery, employmentBookmarkParams);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery,Long.class);
    }

    public Long employmentBookmarkDelete(Long employmentIdx, Long userIdxByJwt){
        String employmentBookmarkDeleteQuery = "delete from Emp_Bookmark where employment_idx = ? and user_idx = ?";
        Object[] employmentBookmarkDeleteParams = new Object[]{employmentIdx, userIdxByJwt};
        this.jdbcTemplate.update(employmentBookmarkDeleteQuery, employmentBookmarkDeleteParams);

        Long deletedEmploymentIdx = employmentIdx;
        return deletedEmploymentIdx;
    }



    public GetEmploymentDTO getEmploymentByEmploymentIdx(Long employmentIdx){
        String getEmploymentByEmploymentIdxQuery1 = "select * from Employment where employment_idx = ?";
        Long getEmploymentByEmploymentIdxParams1 = employmentIdx;
        GetEmploymentRes getEmploymentRes = this.jdbcTemplate.queryForObject(getEmploymentByEmploymentIdxQuery1,
                (rs, rowNum) -> new GetEmploymentRes(
                        rs.getLong("employment_idx"),
                        rs.getLong("company_idx"),
                        rs.getString("emp_title"),
                        rs.getString("job_group"),
                        rs.getString("company_name"),
                        rs.getString("company_location"),
                        rs.getLong("rec_reward"),
                        rs.getLong("vol_reward"),
                        rs.getLong("career"),
                        rs.getString("emp_contents"),
                        rs.getString("emp_deadline"),
                        rs.getString("work_location"),
                        rs.getDate("created"),
                        rs.getDate("updated"),
                        rs.getString("status")),
                getEmploymentByEmploymentIdxParams1);

        String getEmploymentByEmploymentIdxQuery2 = "select company_img_1\n" +
                "from Employment inner join Company on Employment.company_idx = Company.company_idx\n" +
                "inner join Company_Img on Company.company_idx = Company_Img.company_idx\n" +
                "where Employment.employment_idx = ?";
        Long getEmploymentByEmploymentIdxParams2 = employmentIdx;
        String imgUrl = this.jdbcTemplate.queryForObject(getEmploymentByEmploymentIdxQuery2,
                (rs, rowNum) -> new String(
                        rs.getString("company_img_1")),
                getEmploymentByEmploymentIdxParams2);

        String getEmploymentByEmploymentIdxQuery3 = "select count(emp_like_idx) as '좋아요 개수'\n" +
                "from Emp_Like inner join Employment on Emp_Like.employment_idx = Employment.employment_idx\n" +
                "where Emp_Like.employment_idx = ?\n" +
                "group by Emp_Like.employment_idx";
        Long getEmploymentByEmploymentIdxParams3 = employmentIdx;
        Long liked = this.jdbcTemplate.queryForObject(getEmploymentByEmploymentIdxQuery3,
                (rs, rowNum) -> new Long(
                        rs.getLong("좋아요 개수")),
                getEmploymentByEmploymentIdxParams3);


        GetEmploymentDTO getEmploymentDTO = new GetEmploymentDTO(getEmploymentRes, imgUrl, liked);

                return getEmploymentDTO;
    }

    public List<GetEmploymentPageRes> getEmploymentPage(String tag, String location, Long year){
        String getEmploymentPageQuery = "select Employment.employment_idx, VVS.company_img_1, emp_title, VVS.company_name, VVS.company_location, Employment.rec_reward + Employment.vol_reward as 'reward'\n" +
                "from Employment\n" +
                "inner join(select Company.company_idx, Company_Img.company_img_1, Company.company_location,Company.company_name\n" +
                "        from Company inner join Company_Img on Company.company_idx = Company_Img.company_idx\n" +
                "        inner join Com_Key_Map on Company.company_idx = Com_Key_Map.company_idx\n" +
                "        inner join Keyword on Com_Key_Map.keyword_num = Keyword.keyword_num\n" +
                "        where keyword_name = ? and Company.company_location = ?) VVS on VVS.company_idx = Employment.company_idx\n" +
                "where Employment.career = ?";
        String getEmploymentPageParam1 = tag;
        String getEmploymentPageParam2 = location;
        Long getEmploymentPageParam3 = year;
        return this.jdbcTemplate.query(getEmploymentPageQuery,
                (rs, rowNum) -> new GetEmploymentPageRes(
                        rs.getLong("employment_idx"),
                        rs.getString("company_img_1"),
                        rs.getString("emp_title"),
                        rs.getString("company_name"),
                        rs.getString("company_location"),
                        rs.getLong("reward")),
                getEmploymentPageParam1, getEmploymentPageParam2, getEmploymentPageParam3);
    }
}
