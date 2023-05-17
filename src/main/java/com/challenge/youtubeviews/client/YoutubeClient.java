package com.challenge.youtubeviews.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name="youtube-client", url="https://youtube.googleapis.com/youtube/v3/videos")
public interface YoutubeClient {
}
