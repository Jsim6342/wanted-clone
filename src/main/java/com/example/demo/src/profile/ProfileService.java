package com.example.demo.src.profile;

import com.example.demo.src.profile.model.req.SeekStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileDao profileDao;

    public void modifySeekStatus(Long userId, SeekStatus seekStatus) {
        profileDao.modifySeekStatus(userId, seekStatus);
    }

    public void modifyBasicResume(Long userId, Long resumeId) {
        profileDao.modifyBasicResume(userId, resumeId);
    }
}
