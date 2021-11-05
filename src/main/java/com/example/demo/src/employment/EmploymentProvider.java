package com.example.demo.src.employment;


import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
