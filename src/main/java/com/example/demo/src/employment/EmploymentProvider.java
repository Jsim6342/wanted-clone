package com.example.demo.src.employment;


import com.example.demo.com.exception.BaseException;
import com.example.demo.src.employment.model.GetEmploymentPageRes;
import com.example.demo.src.employment.model.GetEmploymentDTO;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.com.exception.BaseResponseStatus.*;



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

    public GetEmploymentDTO getEmploymentByEmploymentIdx(Long employmentIdx) throws BaseException{
        try{
            GetEmploymentDTO getEmploymentDTO = employmentDao.getEmploymentByEmploymentIdx(employmentIdx);
            return getEmploymentDTO;
        }catch (EmptyResultDataAccessException emptyException){
            throw emptyException;
        }
        catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetEmploymentPageRes> getEmploymentPage(String tag, String location, Long year) throws BaseException{
        try{
            List<GetEmploymentPageRes> getEmploymentPageRes = employmentDao.getEmploymentPage(tag, location, year);
            return getEmploymentPageRes;
        } catch (Exception exception){
            System.out.println(exception);
            throw exception;
//            throw new BaseException(DATABASE_ERROR);
        }
    }
}
