package com.challenge.youtubeviews.controller;

import com.challenge.youtubeviews.model.Video;
import com.challenge.youtubeviews.response.VideoInfoResponse;
import com.challenge.youtubeviews.service.VideoService;
import com.challenge.youtubeviews.service.VideoStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/video")
public class VideoController {
    @Autowired
    private VideoService videoService;
    @Autowired
    private VideoStatisticsService videoStatisticsService;

    @PostMapping("/{id}")
    public ResponseEntity<Video> saveVideoToMonitor(@PathVariable(value="id") String id) {
        Video video = videoService.saveVideo(id);
        videoStatisticsService.addStatsToNewVideo(video);

        return ResponseEntity.status(HttpStatus.CREATED).body(video);
    }

    @GetMapping("/monitor")
    public ResponseEntity<List<Video>> getAllVideosMonitored() {
        List<Video> videosMonitored = videoService.getAllVideosMonitored();
        return ResponseEntity.status(HttpStatus.OK).body(videosMonitored);
    }

    @GetMapping("/monitor/{id}")
    public ResponseEntity<VideoInfoResponse> getMonitoredVideoLastStatsById(@PathVariable(value="id") String youtubeUrlId) {
        VideoInfoResponse videoInfoResponse = videoService.getMonitoredVideoLastStatsById(youtubeUrlId);
        return ResponseEntity.status(HttpStatus.OK).body(videoInfoResponse);
    }
}
