package com.example.demo.src.profile;

import com.example.demo.src.profile.model.GetProfileDTO;
import com.example.demo.src.profile.model.MyWantedDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileProvider {

    private final ProfileDao profileDao;

    public MyWantedDTO.ResponseDTO getMyWanted(Long userId) {
        return profileDao.getMyWanted(userId);
    }

    public GetProfileDTO.ResponseDTO getProfile(Long userId) {
        return profileDao.getProfile(userId);
    }
}
