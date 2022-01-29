package com.alkemy.ong.service;

import com.alkemy.ong.dto.ActivityDTO;

public interface ActivityService {
    ActivityDTO createActivity(ActivityDTO activityDTO)throws Exception;
    public void verifyActivity(ActivityDTO activityDTO) throws Exception;
}