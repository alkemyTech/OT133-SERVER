package com.alkemy.ong.seeder;

import com.alkemy.ong.entity.Activity;
import com.alkemy.ong.repository.ActivityRepository;
import java.sql.Timestamp;
import java.time.Instant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ActivitiesSeeder implements CommandLineRunner {

    @Autowired
    private ActivityRepository activityRepository;

    private static final String image = "https://foo.jpg";
    
    @Override
    public void run(String... args) throws Exception {
        loadActivities();
    }

    private void loadActivities() {
        if (activityRepository.count() == 0) {
            loadActivitiesSeed();
        }
    }

    private void loadActivitiesSeed() {
        activityRepository.save(buildActivity("1", "School and family support", "Family support"));
        activityRepository.save(buildActivity("2", "Elementary school support", "Elementary school"));
        activityRepository.save(buildActivity("3", "Middle school support", "Middle school"));
        activityRepository.save(buildActivity("4", "Hight school support", "Hight school"));
        activityRepository.save(buildActivity("5", "Tutorials", "Tutorials"));
    }

    private Activity buildActivity(String id, String name, String content) {
        return new Activity(id,
                name,
                content,
                image,
                Timestamp.from(Instant.now()),
                false);
    }
}
