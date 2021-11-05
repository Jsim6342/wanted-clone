package com.example.demo.src.employment;

import com.example.demo.src.employment.model.PostEmploymentReq;
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
}
