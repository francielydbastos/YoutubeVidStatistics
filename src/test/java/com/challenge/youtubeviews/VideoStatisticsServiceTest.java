package com.challenge.youtubeviews;

import com.challenge.youtubeviews.client.YoutubeClient;
import com.challenge.youtubeviews.exception.VideoNotFoundException;
import com.challenge.youtubeviews.model.Video;
import com.challenge.youtubeviews.model.VideoStatistics;
import com.challenge.youtubeviews.repository.VideoRepository;
import com.challenge.youtubeviews.repository.VideoStatisticsRepository;
import com.challenge.youtubeviews.response.StatsInfoResponse;
import com.challenge.youtubeviews.response.videostatistics.ItensResponse;
import com.challenge.youtubeviews.response.videostatistics.StatisticsResponse;
import com.challenge.youtubeviews.response.videostatistics.VideoStatisticsResponse;
import com.challenge.youtubeviews.service.VideoStatisticsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VideoStatisticsServiceTest {

    @InjectMocks
    private VideoStatisticsService videoStatisticsService;
    @Mock
    private YoutubeClient youtubeClient;
    @Mock
    private VideoRepository videoRepository;
    @Mock
    private VideoStatisticsRepository videoStatisticsRepository;

    @Test
    void testAddStatsToNewVideo() {
        Video video = new Video(1,"idyoutubevideo","video title", LocalDateTime.now(),"chanel title");
        VideoStatisticsResponse videoStatisticsResponse = new VideoStatisticsResponse("", "", List.of(new ItensResponse("","","idyoutubevideo",
                new StatisticsResponse(1L,1L,1L))));

        when(youtubeClient.getVideoStatistics(any(),any(),any())).thenReturn(videoStatisticsResponse);

        videoStatisticsService.addStatsToNewVideo(video);

        verify(videoStatisticsRepository).save(any());
    }

    @Test
    void testUpdateStatistics() {
        List<Video> videos = List.of(new Video(1,"idyoutubevideo","video title", LocalDateTime.now(),"chanel title"));
        VideoStatisticsResponse videoStatisticsResponse = new VideoStatisticsResponse("", "", List.of(new ItensResponse("","","idyoutubevideo",
                new StatisticsResponse(1L,1L,1L))));

        when(videoRepository.findAll()).thenReturn(videos);
        when(youtubeClient.getVideoStatistics(any(),any(),any())).thenReturn(videoStatisticsResponse);

        videoStatisticsService.updateStatistics();

        verify(videoStatisticsRepository).saveAll(any());
    }

    @Test
    void testGetAllStatsForAVideoWithIncorrectId() {
        when(videoRepository.findByYoutubeUrlId(any())).thenReturn(Optional.empty());

        assertThrows(VideoNotFoundException.class,() -> videoStatisticsService.getAllStatsForAVideo(any()));
    }

    @Test
    void testGetAllStatsForAVideoWithCorrectId() {
        Video video = new Video(1,"idyoutubevideo","video title", LocalDateTime.now(),"chanel title");
        VideoStatistics videoStatistics = new VideoStatistics(0,video,Instant.now(),1L,1L,1L);

        when(videoRepository.findByYoutubeUrlId(any())).thenReturn(Optional.of(video));
        when(videoStatisticsRepository.findAllByVideoIdOrderByVideoStatisticsIdDesc(video)).thenReturn(List.of(videoStatistics));

        List<StatsInfoResponse> statsResponse = videoStatisticsService.getAllStatsForAVideo(video.getYoutubeUrlId());

        assertEquals(videoStatistics.getViewCount(), statsResponse.get(0).getViewCount());
        assertEquals(videoStatistics.getCommentCount(), statsResponse.get(0).getCommentCount());
        assertEquals(videoStatistics.getLikeCount(), statsResponse.get(0).getLikeCount());
    }

    @Test
    void testGetTop20StatsForAVideoWithCorrectId() {
        Video video = new Video(1,"idyoutubevideo","video title", LocalDateTime.now(),"chanel title");
        VideoStatistics videoStatistics = new VideoStatistics(0,video,Instant.now(),1L,1L,1L);

        when(videoRepository.findByYoutubeUrlId(any())).thenReturn(Optional.of(video));
        when(videoStatisticsRepository.findTop20ByVideoIdOrderByVideoStatisticsIdDesc(video)).thenReturn(List.of(videoStatistics));

        List<StatsInfoResponse> statsResponse = videoStatisticsService.getTop20StatsForAVideo(video.getYoutubeUrlId());

        assertEquals(videoStatistics.getViewCount(), statsResponse.get(0).getViewCount());
        assertEquals(videoStatistics.getCommentCount(), statsResponse.get(0).getCommentCount());
        assertEquals(videoStatistics.getLikeCount(), statsResponse.get(0).getLikeCount());
    }

    @Test
    void testGetTop20StatsForAVideoWithIncorrectId() {
        when(videoRepository.findByYoutubeUrlId(any())).thenReturn(Optional.empty());

        assertThrows(VideoNotFoundException.class,() -> videoStatisticsService.getAllStatsForAVideo(any()));
    }

}
