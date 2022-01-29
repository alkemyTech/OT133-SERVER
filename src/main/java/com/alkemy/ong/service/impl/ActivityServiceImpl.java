package com.alkemy.ong.repository.service.impl;

import com.alkemy.ong.dto.ActivityDTO;
import com.alkemy.ong.entity.Activity;
import com.alkemy.ong.mapper.ActivityMapper;
import com.alkemy.ong.repository.ActivityRepository;
import com.alkemy.ong.repository.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;

public class ActivityServiceImpl implements ActivityService {
    @Autowired
    private ActivityMapper activityMapper;
    @Autowired
    private ActivityRepository activityRepository;
    @Override
    public ActivityDTO createActivity(ActivityDTO activityDTO) {
        Activity newActivity = activityMapper.activityDTO2Entity(activityDTO);
        Activity savedActivity = activityRepository.save(newActivity);
        return activityMapper.activityEntity2DTO(savedActivity);
    }
}
