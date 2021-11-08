package com.example.demo.src.common;

import com.example.demo.src.common.model.mainDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommonProvider {

    private final CommonDao commonDao;

    public mainDTO.ResponseDTO getMain(Long userId) {
        return commonDao.getMain(userId);
    }
}
