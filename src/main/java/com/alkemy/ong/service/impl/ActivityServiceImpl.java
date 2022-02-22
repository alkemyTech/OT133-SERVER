package com.alkemy.ong.service.impl;

import java.util.Optional;

import com.alkemy.ong.dto.ActivityDTO;
import com.alkemy.ong.dto.TestimonialDTO;
import com.alkemy.ong.entity.Activity;
import com.alkemy.ong.entity.Testimonial;
import com.alkemy.ong.exception.ActivityException;
import com.alkemy.ong.mapper.ActivityMapper;
import com.alkemy.ong.repository.ActivityRepository;
import com.alkemy.ong.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityServiceImpl implements ActivityService {
	@Autowired
	private ActivityMapper activityMapper;
	@Autowired
	private ActivityRepository activityRepository;

	@Override
	public ActivityDTO createActivity(ActivityDTO activityDTO) throws Exception {
		Activity newActivity = activityMapper.activityDTO2Entity(activityDTO);
		Activity savedActivity = activityRepository.save(newActivity);
		return activityMapper.activityEntity2DTO(savedActivity);
	}

	@Override
	public ActivityDTO updateActivity(String id, ActivityDTO activityToUpdate){
		Optional<Activity> activityOptional = activityRepository.findById(id);
		if(activityOptional.isPresent()){
			Activity activity = activityOptional.get();
			activity.setName(activityToUpdate.getName());
			activity.setContent(activityToUpdate.getContent());
			activity.setImage(activityToUpdate.getImage());
			activityRepository.save(activity);
			return activityMapper.activityEntity2DTO(activity);
		} return null;
	}

  public ActivityDTO findById(String id) {
    Activity activity = activityRepository.findById(id).get();
    return activityMapper.activityEntity2DTO(activity);
  }
}
