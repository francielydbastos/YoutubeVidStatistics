package com.challenge.youtubeviews.controller;

import com.challenge.youtubeviews.model.Video;
import com.challenge.youtubeviews.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/video")
public class VideoController {
    @Autowired
    private VideoService videoService;

    @PostMapping("/{id}")
    public ResponseEntity<Video> saveVideoToMonitor(@PathVariable(value="id") String id) {
        Video video = videoService.saveVideo(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(video);
    }
}
