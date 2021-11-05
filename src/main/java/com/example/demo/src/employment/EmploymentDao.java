package com.example.demo.src.employment;

import com.example.demo.src.employment.model.GetEmploymentRes;
import com.example.demo.src.employment.model.PostEmploymentReq;
import com.example.demo.src.user.model.GetUserRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

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
                postEmploymentReq.getWorkLocation(),
                postEmploymentReq.getRecReward(),
                postEmploymentReq.getVolReward(),
                postEmploymentReq.getEmpContents(),
                postEmploymentReq.getEmpDeadline(),
                postEmploymentReq.getWorkLocation()};
        this.jdbcTemplate.update(createEmploymentQuery,createEmploymentParams);

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
}
