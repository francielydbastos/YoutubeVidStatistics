package com.challenge.youtubeviews.client;

import com.challenge.youtubeviews.response.video.VideoResponse;
import com.challenge.youtubeviews.response.videostatistics.VideoStatisticsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="youtube-client", url="https://youtube.googleapis.com/youtube/v3/videos")
public interface YoutubeClient {

    @GetMapping
    VideoResponse getVideo(@RequestParam("part") String part, @RequestParam("id") String id, @RequestParam("key") String key);

    @GetMapping
    VideoStatisticsResponse getVideoStatistics(@RequestParam("part") String part, @RequestParam("id") String id, @RequestParam("key") String key);

}
