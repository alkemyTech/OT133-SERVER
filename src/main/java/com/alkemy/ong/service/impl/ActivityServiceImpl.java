package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.ActivityDTO;
import com.alkemy.ong.entity.Activity;
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
		verifyActivity(activityDTO);
		Activity newActivity = activityMapper.activityDTO2Entity(activityDTO);

		Activity savedActivity = activityRepository.save(newActivity);
		// System.out.println("activitiDToControlador " +
		// savedActivity.getTimestamps());
		return activityMapper.activityEntity2DTO(savedActivity);
	}

	public void verifyActivity(ActivityDTO activityDTO) throws ActivityException {
		
		if (activityDTO.getName() == null ||activityDTO.getName().isEmpty()) {
			throw new ActivityException("Name null or empty");
		}
		if (activityDTO.getContent() == null||activityDTO.getName().isEmpty()) {
			throw new ActivityException("Content nulo or empty");
		}
		if (activityDTO.getImage() == null||activityDTO.getName().isEmpty()) {
			throw new ActivityException("Image null or empty");
		}
		if (activityDTO.getTimestamps() == null||activityDTO.getName().isEmpty()) {
			throw new ActivityException("Timestamps null or empty");
		}

	}

}
