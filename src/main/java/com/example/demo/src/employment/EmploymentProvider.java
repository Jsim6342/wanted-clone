package com.example.demo.src.employment;


import com.example.demo.config.exception.BaseException;
import com.example.demo.config.response.BaseResponse;
import com.example.demo.config.response.BaseResponseStatus;
import com.example.demo.src.employment.model.GetEmploymentRes;
import com.example.demo.src.user.model.GetUserRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import static com.example.demo.config.response.BaseResponseStatus.*;



@Service
public class EmploymentProvider {

    final Logger logger = LoggerFactory.getLogger(this.getClass());


    private final EmploymentDao employmentDao;
    private final JwtService jwtService;

    @Autowired
    public EmploymentProvider(EmploymentDao employmentDao, JwtService jwtService) {
        this.employmentDao = employmentDao;
        this.jwtService = jwtService;
    }

    public GetEmploymentRes getEmploymentByEmploymentIdx(Long employmentIdx) throws BaseException{
        try{
            GetEmploymentRes getEmploymentRes = employmentDao.getEmploymentByEmploymentIdx(employmentIdx);
            return getEmploymentRes;
        }catch (EmptyResultDataAccessException emptyException){
            throw emptyException;
        }
        catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
