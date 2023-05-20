package com.challenge.youtubeviews.service;

import com.challenge.youtubeviews.client.YoutubeClient;
import com.challenge.youtubeviews.exception.VideoNotFoundException;
import com.challenge.youtubeviews.model.Video;
import com.challenge.youtubeviews.model.VideoStatistics;
import com.challenge.youtubeviews.repository.VideoRepository;
import com.challenge.youtubeviews.repository.VideoStatisticsRepository;
import com.challenge.youtubeviews.response.StatsInfoResponse;
import com.challenge.youtubeviews.response.videostatistics.ItensResponse;
import com.challenge.youtubeviews.response.videostatistics.VideoStatisticsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VideoStatisticsService {
    @Value("${API_KEY}")
    private String API_KEY;

    @Autowired
    private YoutubeClient youtubeClient;
    @Autowired
    private VideoStatisticsRepository videoStatisticsRepository;
    @Autowired
    private VideoRepository videoRepository;

    private VideoStatisticsResponse getVideoDetails(List<String> youtubeVideoIds) {
        return youtubeClient.getVideoStatistics("statistics", youtubeVideoIds, API_KEY);
    }
    public void updateViews() {
        List<Video> videos = videoRepository.findAll();
        List<String> youtubeVideoIds = videos.stream().map(Video::getYoutubeUrlId).toList();
        VideoStatisticsResponse videoStatisticsResponse = getVideoDetails(youtubeVideoIds);

        List<VideoStatistics> videoStatisticsList = new ArrayList<>();

        List<ItensResponse> itensStatistic = videoStatisticsResponse.getItems();

        itensStatistic.forEach((item) -> {
            VideoStatistics videoStatistics = new VideoStatistics();
            videoStatistics.setVideoId(videos.get(itensStatistic.indexOf(item)));
            videoStatistics.setViewCount(item.getStatistics().getViewCount());
            videoStatistics.setCommentCount(item.getStatistics().getCommentCount());
            videoStatistics.setLikeCount(item.getStatistics().getLikeCount());
            videoStatistics.setUpdateDate(Instant.now());

            videoStatisticsList.add(videoStatistics);
        });

        videoStatisticsRepository.saveAll(videoStatisticsList);
    }

    public List<StatsInfoResponse> getAllStatsForAVideo(String youtubeVideoId) {
        Optional<Video> optionalVideo = videoRepository.findByYoutubeUrlId(youtubeVideoId);

        if (optionalVideo.isEmpty()) {
            throw new VideoNotFoundException();
        }

       List<VideoStatistics> allVideoStats = videoStatisticsRepository.findAllByVideoIdOrderByVideoStatisticsIdDesc(optionalVideo.get());
       List<StatsInfoResponse> allStatsInfo = allVideoStats.stream().map(video -> new StatsInfoResponse(video.getUpdateDate(), video.getViewCount(), video.getLikeCount(), video.getCommentCount())).toList();

       return allStatsInfo;
    }

    public List<StatsInfoResponse> getTop20StatsForAVideo(String youtubeVideoId) {
        Optional<Video> optionalVideo = videoRepository.findByYoutubeUrlId(youtubeVideoId);

        if (optionalVideo.isEmpty()) {
            throw new VideoNotFoundException();
        }

        List<VideoStatistics> top20VideoStats = videoStatisticsRepository.findTop20ByVideoIdOrderByVideoStatisticsIdDesc(optionalVideo.get());
        List<StatsInfoResponse> top20StatsInfo = top20VideoStats.stream().map(video -> new StatsInfoResponse(video.getUpdateDate(), video.getViewCount(), video.getLikeCount(), video.getCommentCount())).toList();

        return top20StatsInfo;
    }

}
