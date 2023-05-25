package com.challenge.youtubeviews;

import com.challenge.youtubeviews.controller.VideoController;
import com.challenge.youtubeviews.exception.VideoAlreadySavedInDbException;
import com.challenge.youtubeviews.exception.VideoNotFoundException;
import com.challenge.youtubeviews.jsonresources.LocalDateTimeTypeAdapter;
import com.challenge.youtubeviews.model.Video;
import com.challenge.youtubeviews.model.VideoStatistics;
import com.challenge.youtubeviews.response.VideoInfoResponse;
import com.challenge.youtubeviews.service.VideoService;
import com.challenge.youtubeviews.service.VideoStatisticsService;
import com.google.gson.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VideoController.class)
public class VideoControllerTest {
    @MockBean
    VideoService videoService;

    @MockBean
    VideoStatisticsService videoStatisticsService;

    @Autowired
    private MockMvc mvc;

    @Test
    void testSaveVideoToMonitorWithCorrectVideoId() throws Exception {
        Video video = new Video(1,"idyoutubevideo","video title", LocalDateTime.now(),"chanel title");

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                .create();

        String videoResponse = gson.toJson(video);

        when(videoService.saveVideo("idyoutubevideo")).thenReturn(video);

        RequestBuilder request = post("/video/idyoutubevideo");
        MvcResult result = mvc.perform(request).andExpect(status().isCreated()).andReturn();

        assertEquals(videoResponse, result.getResponse().getContentAsString());
    }

    @Test
    void testSaveVideoToMonitorWithIncorrectVideoId() throws Exception {

        when(videoService.saveVideo("idyoutubevideo")).thenThrow(VideoNotFoundException.class);

        RequestBuilder request = post("/video/idyoutubevideo");
        mvc.perform(request).andExpect(status().isNotFound()).andReturn();
    }

    @Test
    void testSaveVideoToMonitorWithSavedVideoId() throws Exception {

        when(videoService.saveVideo("idyoutubevideo")).thenThrow(VideoAlreadySavedInDbException.class);

        RequestBuilder request = post("/video/idyoutubevideo");
        mvc.perform(request).andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    void testGetAllVideosMonitored() throws Exception {

        List<Video> videos = List.of(new Video(1,"idyoutubevideo","video title", LocalDateTime.now(),"chanel title"));

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                .create();

        String videoResponse = gson.toJson(videos);

        when(videoService.getAllVideosMonitored()).thenReturn(videos);

        RequestBuilder request = get("/video/monitor");
        MvcResult result = mvc.perform(request).andExpect(status().isOk()).andReturn();

        assertEquals(videoResponse, result.getResponse().getContentAsString());
    }

    @Test
    void testGetMonitoredVideoLastStatsByIdWithIncorrectId() throws Exception {

        when(videoService.getMonitoredVideoLastStatsById("idyoutubevideo")).thenThrow(VideoNotFoundException.class);

        RequestBuilder request = get("/video/monitor/idyoutubevideo");
        mvc.perform(request).andExpect(status().isNotFound()).andReturn();
    }

    @Test
    void testGetMonitoredVideoLastStatsByIdWithCorrectId() throws Exception {

        Video video = new Video(1,"idyoutubevideo","video title", LocalDateTime.now(),"chanel title");
        VideoStatistics videoStatistics = new VideoStatistics(1L,video, Instant.now(),1L,1L,1L);
        VideoInfoResponse videoInfoResponse = new VideoInfoResponse(video.getVideoTitle(), video.getUploadDate(), video.getChanelTitle(), videoStatistics.getViewCount(),
                videoStatistics.getLikeCount(), videoStatistics.getCommentCount());

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                .create();

        String videoResponse = gson.toJson(videoInfoResponse);

        when(videoService.getMonitoredVideoLastStatsById("idyoutubevideo")).thenReturn(videoInfoResponse);

        RequestBuilder request = get("/video/monitor/idyoutubevideo");
        MvcResult result = mvc.perform(request).andExpect(status().isOk()).andReturn();

        assertEquals(videoResponse, result.getResponse().getContentAsString());
    }

}
