package com.challenge.youtubeviews.controller;

import com.challenge.youtubeviews.response.StatsInfoResponse;
import com.challenge.youtubeviews.service.VideoStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/videoStatistics")
public class VideoStatisticsController {
    @Autowired
    private VideoStatisticsService videoStatisticsService;

    @GetMapping("/updateStatistics")
    public void updateStatistics() {
        videoStatisticsService.updateStatistics();
    }

    @GetMapping("/{id}/all")
    public ResponseEntity<List<StatsInfoResponse>> getAllStatsForAVideo(@PathVariable(value="id") String youtubeUrlId) {
        List<StatsInfoResponse> allStats = videoStatisticsService.getAllStatsForAVideo(youtubeUrlId);
        return ResponseEntity.status(HttpStatus.OK).body(allStats);
    }

    @GetMapping("/{id}/top20")
    public ResponseEntity<List<StatsInfoResponse>> getTop20StatsForAVideo(@PathVariable(value="id") String youtubeUrlId) {
        List<StatsInfoResponse> top20Stats = videoStatisticsService.getTop20StatsForAVideo(youtubeUrlId);
        return ResponseEntity.status(HttpStatus.OK).body(top20Stats);
    }
}
