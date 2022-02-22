package com.alkemy.ong.service;

import com.alkemy.ong.dto.ActivityDTO;

public interface ActivityService {
    ActivityDTO createActivity(ActivityDTO activityDTO)throws Exception;
    ActivityDTO updateActivity(String id, ActivityDTO activity);
    ActivityDTO findById(String id);
}