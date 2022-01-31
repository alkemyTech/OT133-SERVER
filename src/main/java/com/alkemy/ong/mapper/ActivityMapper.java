package com.alkemy.ong.mapper;

import com.alkemy.ong.dto.ActivityDTO;
import com.alkemy.ong.entity.Activity;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.springframework.stereotype.Component;

@Component
public class ActivityMapper {
	public Activity activityDTO2Entity(ActivityDTO dto) {
		Activity activity = new Activity();
		activity.setName(dto.getName());
		activity.setImage(dto.getImage());
		activity.setContent(dto.getContent());
		activity.setTimestamps(string2LocalDate(dto.getTimestamps()));
		return activity;
	}

	public ActivityDTO activityEntity2DTO(Activity activity) {
		ActivityDTO dto = new ActivityDTO();
		dto.setName(activity.getName());
		dto.setImage(activity.getImage());
		dto.setContent(activity.getContent());
		dto.setTimestamps(LocalDate2String(activity.getTimestamps()));
		return dto;
	}

	public LocalDateTime string2LocalDate(String stringDate) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		return LocalDateTime.parse(stringDate, formatter);
	}

	public String LocalDate2String(LocalDateTime localDateTime) {
		DateTimeFormatter isoFecha = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		return localDateTime.format(isoFecha);
	}

}
