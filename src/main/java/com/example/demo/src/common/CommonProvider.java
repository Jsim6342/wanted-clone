package com.example.demo.src.common;

import com.example.demo.src.common.model.mainDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommonProvider {

    private final CommonDao commonDao;

    public mainDTO.ResponseDTO getMain(Long userId) {
        return commonDao.getMain(userId);
    }
}
