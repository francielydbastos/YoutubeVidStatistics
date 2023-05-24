package com.challenge.youtubeviews;

import com.challenge.youtubeviews.client.YoutubeClient;
import com.challenge.youtubeviews.exception.VideoAlreadySavedInDbException;
import com.challenge.youtubeviews.exception.VideoNotFoundException;
import com.challenge.youtubeviews.model.Video;
import com.challenge.youtubeviews.model.VideoStatistics;
import com.challenge.youtubeviews.repository.VideoRepository;
import com.challenge.youtubeviews.repository.VideoStatisticsRepository;
import com.challenge.youtubeviews.response.VideoInfoResponse;
import com.challenge.youtubeviews.response.video.ItensResponse;
import com.challenge.youtubeviews.response.video.SnippetResponse;
import com.challenge.youtubeviews.response.video.VideoResponse;
import com.challenge.youtubeviews.service.VideoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VideoServiceTest {

    @InjectMocks
    private VideoService videoService;
    @Mock
    private YoutubeClient youtubeClient;
    @Mock
    private VideoRepository videoRepository;
    @Mock
    private VideoStatisticsRepository videoStatisticsRepository;

    @Test
    void testSaveVideoWithIncorrectId() {
        VideoResponse videoResponse = new VideoResponse("", "", new ArrayList<>());

        when(youtubeClient.getVideo(any(),any(),any())).thenReturn(videoResponse);
        assertThrows(VideoNotFoundException.class,() -> videoService.saveVideo(any()));
    }

    @Test
    void testSaveVideoWithCorrectIdAndNewVideo() {
        VideoResponse videoResponse = new VideoResponse("", "", List.of(new ItensResponse("","","idyoutubevideo",
                new SnippetResponse(LocalDateTime.now(),"",""))));

        when(videoRepository.findByYoutubeUrlId(videoResponse.getItems().get(0).getId())).thenReturn(Optional.empty());
        when(youtubeClient.getVideo("snippet","idyoutubevideo",null)).thenReturn(videoResponse);

        Video video = videoService.saveVideo(videoResponse.getItems().get(0).getId());

        verify(videoRepository).findByYoutubeUrlId(any());
        verify(videoRepository).save(any());

        assertNotNull(video);
        assertEquals(videoResponse.getItems().get(0).getId(),video.getYoutubeUrlId());
    }

    @Test
    void testSaveVideoWithCorrectIdAndAlreadySavedVideo() {
        when(videoRepository.findByYoutubeUrlId(any())).thenReturn(Optional.of(new Video()));

        assertThrows(VideoAlreadySavedInDbException.class,() -> videoService.saveVideo(any()));
    }

    @Test
    void testGetAllVideosMonitored() {
        List<Video> videos = List.of(new Video(), new Video());

        when(videoRepository.findAll()).thenReturn(videos);

        assertEquals(videos, videoService.getAllVideosMonitored());
    }

    @Test
    void testGetMonitoredVideoLastStatsByIdWithIncorrectId() {
        when(videoRepository.findByYoutubeUrlId(any())).thenReturn(Optional.empty());

        assertThrows(VideoNotFoundException.class,() -> videoService.getMonitoredVideoLastStatsById(any()));
    }

    @Test
    void testGetMonitoredVideoLastStatsByIdWithCorrectId() {
        Video video = new Video(1,"abc","video title",LocalDateTime.now(),"chanel title");
        VideoStatistics videoStatistics = new VideoStatistics(1L,video,Instant.now(),1L,1L,1L);
        VideoInfoResponse videoInfoResponse = new VideoInfoResponse(video.getVideoTitle(), video.getUploadDate(), video.getChanelTitle(), videoStatistics.getViewCount(),
                videoStatistics.getLikeCount(), videoStatistics.getCommentCount());

        when(videoRepository.findByYoutubeUrlId(video.getYoutubeUrlId())).thenReturn(Optional.of(video));
        when(videoStatisticsRepository.findFirstByVideoIdOrderByVideoStatisticsIdDesc(video)).thenReturn(Optional.of(videoStatistics));

        VideoInfoResponse videoInfoResponseReturned = videoService.getMonitoredVideoLastStatsById(video.getYoutubeUrlId());

        assertEquals(videoInfoResponse.getVideoTitle(), videoInfoResponseReturned.getVideoTitle());
        assertEquals(videoInfoResponse.getViewCount(), videoInfoResponseReturned.getViewCount());

    }

}
