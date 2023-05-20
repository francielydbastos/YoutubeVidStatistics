package com.challenge.youtubeviews.service;

import com.challenge.youtubeviews.client.YoutubeClient;
import com.challenge.youtubeviews.exception.NoStatisticsFoundInDbException;
import com.challenge.youtubeviews.exception.VideoAlreadySavedInDbException;
import com.challenge.youtubeviews.exception.VideoNotFoundException;
import com.challenge.youtubeviews.model.Video;
import com.challenge.youtubeviews.model.VideoStatistics;
import com.challenge.youtubeviews.repository.VideoRepository;
import com.challenge.youtubeviews.repository.VideoStatisticsRepository;
import com.challenge.youtubeviews.response.VideoInfoResponse;
import com.challenge.youtubeviews.response.video.SnippetResponse;
import com.challenge.youtubeviews.response.video.VideoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VideoService {
    @Value("${API_KEY}")
    private String API_KEY;

    @Autowired
    private YoutubeClient youtubeClient;
    @Autowired
    private VideoRepository videoRepository;
    @Autowired
    private VideoStatisticsRepository videoStatisticsRepository;

    private VideoResponse getVideoDetails(String id) {
        return youtubeClient.getVideo("snippet", id, API_KEY);
    }
    public Video saveVideo(String youtubeVideoId) {
        if (videoRepository.findByYoutubeUrlId(youtubeVideoId).isPresent()) {
            throw new VideoAlreadySavedInDbException();
        }

        VideoResponse videoDetails = getVideoDetails(youtubeVideoId);
        SnippetResponse snippet = videoDetails.getItems().size() != 0 ? videoDetails.getItems().get(0).getSnippet() : null;

        if (snippet == null) {
            throw new VideoNotFoundException();
        }

        Video video = new Video();
        video.setVideoTitle(snippet.getTitle());
        video.setChanelTitle(snippet.getChannelTitle());
        video.setUploadDate(snippet.getPublishedAt());
        video.setYoutubeUrlId(youtubeVideoId);

        return videoRepository.save(video);
    }

    public List<Video> getAllVideosMonitored() {
        return videoRepository.findAll();
    }

    public VideoInfoResponse getMonitoredVideoLastStatsById(String youtubeVideoId) {
        Optional<Video> optionalVideo = videoRepository.findByYoutubeUrlId(youtubeVideoId);

        if (optionalVideo.isEmpty()) {
            throw new VideoNotFoundException();
        }

        Video video = optionalVideo.get();

        Optional<VideoStatistics> optionalLastVideoStatistics = videoStatisticsRepository.findFirstByVideoIdOrderByVideoStatisticsIdDesc(video);

        if (optionalLastVideoStatistics.isEmpty()) {
            throw new NoStatisticsFoundInDbException();
        }

        VideoStatistics lastVideoStatistics = optionalLastVideoStatistics.get();

        return new VideoInfoResponse(video.getVideoTitle(), video.getUploadDate(), video.getChanelTitle(), lastVideoStatistics.getViewCount(),
                lastVideoStatistics.getLikeCount(), lastVideoStatistics.getCommentCount());
    }
}
