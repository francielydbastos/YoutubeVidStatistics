package com.challenge.youtubeviews.schedule;

import com.challenge.youtubeviews.service.VideoStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduler {

    @Autowired
    VideoStatisticsService videoStatisticsService;

    @Scheduled(fixedRate = 300000)
    public void updateStatistics() {
        videoStatisticsService.updateStatistics();
    }
}
