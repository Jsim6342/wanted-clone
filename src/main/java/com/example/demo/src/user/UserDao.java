package com.example.demo.src.user;


import com.example.demo.src.user.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class UserDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetUserRes> getUsers(){
        String getUsersQuery = "select * from User";
        return this.jdbcTemplate.query(getUsersQuery,
                (rs,rowNum) -> new GetUserRes(
                        rs.getLong("user_idx"),
                        rs.getString("user_email"),
                        rs.getString("user_password"),
                        rs.getString("user_name"),
                        rs.getString("user_phone_number"),
                        rs.getLong("basic_resume_idx"),
                        rs.getString("seek_status"),
                        rs.getLong("point"),
                        rs.getInt("receive_info"),
                        rs.getInt("event_alarm"),
                        rs.getString("authority_level"),
                        rs.getInt("oauth2"),
                        rs.getInt("auto_login"),
                        rs.getDate("created"),
                        rs.getDate("updated"),
                        rs.getString("status"))
                );
    }

    public GetUserRes getUser(Long userIdxByJwt){
        String getUsersQuery = "select * from User where user_idx = ?";
        Long getUserByUserIdxByJwt = userIdxByJwt;
        return this.jdbcTemplate.queryForObject(getUsersQuery,
                (rs,rowNum) -> new GetUserRes(
                        rs.getLong("user_idx"),
                        rs.getString("user_email"),
                        rs.getString("user_password"),
                        rs.getString("user_name"),
                        rs.getString("user_phone_number"),
                        rs.getLong("basic_resume_idx"),
                        rs.getString("seek_status"),
                        rs.getLong("point"),
                        rs.getInt("receive_info"),
                        rs.getInt("event_alarm"),
                        rs.getString("authority_level"),
                        rs.getInt("oauth2"),
                        rs.getInt("auto_login"),
                        rs.getDate("created"),
                        rs.getDate("updated"),
                        rs.getString("status")),
                getUserByUserIdxByJwt
        );
    }

    public GetUserRes getUsersByEmail(String email){
        String getUsersByEmailQuery = "select * from User where user_email =?";
        String getUsersByEmailParams = email;
        return this.jdbcTemplate.queryForObject(getUsersByEmailQuery,
                (rs,rowNum) -> new GetUserRes(
                        rs.getLong("user_idx"),
                        rs.getString("user_email"),
                        rs.getString("user_password"),
                        rs.getString("user_name"),
                        rs.getString("user_phone_number"),
                        rs.getLong("basic_resume_idx"),
                        rs.getString("seek_status"),
                        rs.getLong("point"),
                        rs.getInt("receive_info"),
                        rs.getInt("event_alarm"),
                        rs.getString("authority_level"),
                        rs.getInt("oauth2"),
                        rs.getInt("auto_login"),
                        rs.getDate("created"),
                        rs.getDate("updated"),
                        rs.getString("status")),
                getUsersByEmailParams
        );
    }

    public Long createUser(PostUserReq postUserReq){
        String createUserQuery = "insert into User (user_email, user_name, user_password, user_phone_number) VALUES (?,?,?,?)";
        Object[] createUserParams = new Object[]{postUserReq.getUserEmail(), postUserReq.getUserName(), postUserReq.getUserPassword(), postUserReq.getUserPhoneNumber()};
        this.jdbcTemplate.update(createUserQuery, createUserParams);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery,Long.class);
    }

    public int checkEmail(String email){
        String checkEmailQuery = "select exists(select email from UserInfo where email = ?)";
        String checkEmailParams = email;
        return this.jdbcTemplate.queryForObject(checkEmailQuery,
                int.class,
                checkEmailParams);

    }

    public int modifyUserInfo(PatchUserReq patchUserReq, Long userIdxByJwt){
        String modifyUserInfoQuery = "update User set user_name = ?, user_email = ?, user_phone_number = ?  where user_idx = ? ";
        Object[] modifyUserInfoParams = new Object[]{patchUserReq.getUserName(), patchUserReq.getUserEmail(), patchUserReq.getUserPhoneNumber(), userIdxByJwt};

        return this.jdbcTemplate.update(modifyUserInfoQuery,modifyUserInfoParams);
    }

    public int modifyUserStatus(Long userIdxByJwt){
        String modifyUserStatusQuery = "update User set status = \"INACTIVE\" where user_idx = ?";
        Long modifyUserStatusParams = userIdxByJwt;

        return this.jdbcTemplate.update(modifyUserStatusQuery, modifyUserStatusParams);
    }

    public User getUserInfo(PostLoginReq postLoginReq){
        String getPwdQuery = "select user_idx, user_email, user_password, user_name, user_phone_number, basic_resume_idx, seek_status, point, receive_info, event_alarm, authority_level, oauth2, auto_login, created, updated ,status from User where user_email = ?";
        String getPwdParams = postLoginReq.getUserEmail();

        return this.jdbcTemplate.queryForObject(getPwdQuery,
                (rs,rowNum)-> new User(
                        rs.getLong("user_idx"),
                        rs.getString("user_email"),
                        rs.getString("user_password"),
                        rs.getString("user_name"),
                        rs.getString("user_phone_number"),
                        rs.getLong("basic_resume_idx"),
                        rs.getString("seek_status"),
                        rs.getLong("point"),
                        rs.getLong("receive_info"),
                        rs.getLong("event_alarm"),
                        rs.getString("authority_level"),
                        rs.getLong("oauth2"),
                        rs.getLong("auto_login"),
                        rs.getDate("created"),
                        rs.getDate("updated"),
                        rs.getString("status")),
                getPwdParams);
    }


}
