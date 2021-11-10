package com.example.demo.src.common;

import com.example.demo.src.common.model.mainDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CommonDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public mainDTO.ResponseDTO getMain(Long userId) {

        // 배너 정보 SELECT
        String bannerSql = "select image_url, link_url, title, sub_title from Banner order by banner_idx desc limit 5";
        List<mainDTO.Banner> bannerList = this.jdbcTemplate.query(bannerSql,
                (rs, rowNum) -> new mainDTO.Banner(
                        rs.getString("image_url"),
                        rs.getString("link_url"),
                        rs.getString("title"),
                        rs.getString("sub_title")));


        // 채용 정보 SELECT
        String specializedSql = "select field from Specialized where user_idx = ?";
        List<String> fieldList = this.jdbcTemplate.query(specializedSql,
                (rs, rowNum) -> rs.getString("field"),
                userId);

        List<mainDTO.Employment> employmentList = new ArrayList<>();


        if(!fieldList.isEmpty()) {
            StringBuffer br = new StringBuffer();
            br.append("select ci.company_img_1, e.employment_idx, e.emp_title, e.company_name, e.company_location, ");
            br.append("(e.rec_reward + e.vol_reward) as reward from Employment e ");
            br.append("INNER JOIN Company c on e.company_idx = c.company_idx ");
            br.append("INNER JOIN Company_Img ci on c.company_idx = ci.company_idx ");
            br.append("where e.job_group = ? order by e.employment_idx desc limit 4");

            String sql = br.toString();
            String param = fieldList.get(0);
            employmentList = this.jdbcTemplate.query(sql,
                    (rs, rowNum) -> new mainDTO.Employment(
                            rs.getString("company_img_1"),
                            rs.getLong("employment_idx"),
                            rs.getString("emp_title"),
                            rs.getString("company_name"),
                            rs.getString("company_location"),
                            rs.getLong("reward")),
                    param);

            return mainDTO.ResponseDTO.builder()
                    .banner(bannerList)
                    .employmentList(employmentList)
                    .build();
        }

        StringBuffer br = new StringBuffer();
        br.append("select ci.company_img_1, e.employment_idx, e.emp_title, e.company_name, e.company_location, ");
        br.append("(e.rec_reward + e.vol_reward) as reward from Employment e ");
        br.append("INNER JOIN Company c on e.company_idx = c.company_idx ");
        br.append("INNER JOIN Company_Img ci on c.company_idx = ci.company_idx ");
        br.append("order by e.employment_idx desc limit 4");

        String sql = br.toString();
        employmentList = this.jdbcTemplate.query(sql,
                (rs, rowNum) -> new mainDTO.Employment(
                        rs.getString("company_img_1"),
                        rs.getLong("employment_idx"),
                        rs.getString("emp_title"),
                        rs.getString("company_name"),
                        rs.getString("company_location"),
                        rs.getLong("reward")));

        return mainDTO.ResponseDTO.builder()
                .banner(bannerList)
                .employmentList(employmentList)
                .build();

    }
}
