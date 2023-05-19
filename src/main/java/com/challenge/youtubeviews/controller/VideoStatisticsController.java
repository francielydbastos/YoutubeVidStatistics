package com.challenge.youtubeviews.controller;

import com.challenge.youtubeviews.service.VideoStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/videoStatistics")
public class VideoStatisticsController {
    @Autowired
    private VideoStatisticsService videoStatisticsService;

    @GetMapping("/updateStatistics")
    public void updateStatistics() {
        videoStatisticsService.updateViews();
    }
}
