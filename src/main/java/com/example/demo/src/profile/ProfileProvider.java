package com.example.demo.src.profile;

import com.example.demo.src.profile.model.res.ApplicationDTO;
import com.example.demo.src.profile.model.res.GetProfileDTO;
import com.example.demo.src.profile.model.res.MyWantedDTO;
import com.example.demo.src.profile.model.res.OffersRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProfileProvider {

    private final ProfileDao profileDao;

    public MyWantedDTO.ResponseDTO getMyWanted(Long userId) {
        return profileDao.getMyWanted(userId);
    }

    public GetProfileDTO.ResponseDTO getProfile(Long userId) {
        return profileDao.getProfile(userId);
    }

    public List<ApplicationDTO.ResponseDTO> getApplicationWriting(Long userId) {
        return profileDao.getApplicationWriting(userId);
    }

    public List<ApplicationDTO.ResponseDTO> getApplications(Long userId) {
        return profileDao.getApplications(userId);
    }

    public List<OffersRes> getOffers(Long userId, String type) {
        List<OffersRes> result = new ArrayList<>();

        if(type.equals("matchup-all")) {
            result = profileDao.getAllOffers(userId);
        }
        if(type.equals("matchup-likes")) {
            result = profileDao.getLikesOffers(userId);
        }
        if(type.equals("matchup-opens")) {
            result = profileDao.getOpensOffers(userId);
        }
        if(type.equals("matchup-offers")) {
            result = profileDao.getOffers(userId);
        }

        return result;
    }
}
