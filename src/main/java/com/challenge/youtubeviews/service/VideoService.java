package com.challenge.youtubeviews.service;

import com.challenge.youtubeviews.client.YoutubeClient;
import com.challenge.youtubeviews.model.Video;
import com.challenge.youtubeviews.repository.VideoRepository;
import com.challenge.youtubeviews.response.SnippetResponse;
import com.challenge.youtubeviews.response.VideoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class VideoService {
    @Value("${API_KEY}")
    private String API_KEY;

    @Autowired
    private YoutubeClient youtubeClient;
    @Autowired
    private VideoRepository videoRepository;

    public VideoResponse getVideoDetails(String id) {
        return youtubeClient.getVideo("snippet", id, API_KEY);
    }
    public Video saveVideo(String youtubeVideoId) {
        VideoResponse videoDetails = getVideoDetails(youtubeVideoId);
        SnippetResponse snippet = videoDetails.getItems().get(0) != null ? videoDetails.getItems().get(0).getSnippet() : null;

        if (snippet == null) {
            throw new RuntimeException("No video found with the provided ID.");
        }

        Video video = Video.builder()
                .videoTitle(snippet.getTitle())
                .chanelTitle(snippet.getChannelTitle())
                .uploadDate(snippet.getPublishedAt())
                .youtubeUrlId(youtubeVideoId)
                .build();

        return videoRepository.save(video);
    }

    
}
