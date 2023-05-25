package com.challenge.youtubeviews;

import com.challenge.youtubeviews.controller.VideoStatisticsController;
import com.challenge.youtubeviews.exception.VideoNotFoundException;
import com.challenge.youtubeviews.jsonresources.InstantTypeAdapter;
import com.challenge.youtubeviews.model.Video;
import com.challenge.youtubeviews.model.VideoStatistics;
import com.challenge.youtubeviews.response.StatsInfoResponse;
import com.challenge.youtubeviews.service.VideoService;
import com.challenge.youtubeviews.service.VideoStatisticsService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VideoStatisticsController.class)
public class VideoStatisticsControllerTest {
    @MockBean
    VideoService videoService;

    @MockBean
    VideoStatisticsService videoStatisticsService;

    @Autowired
    private MockMvc mvc;

    @Test
    void testUpdateStatistics() throws Exception {

        RequestBuilder request = get("/videoStatistics/updateStatistics");
        mvc.perform(request).andExpect(status().isOk()).andReturn();
    }

    @Test
    void testGetAllStatsForAVideoWithIncorrectId() throws Exception {

        when(videoStatisticsService.getAllStatsForAVideo("idyoutubevideo")).thenThrow(VideoNotFoundException.class);

        RequestBuilder request = get("/videoStatistics/idyoutubevideo/all");
        mvc.perform(request).andExpect(status().isNotFound()).andReturn();
    }

    @Test
    void testGetAllStatsForAVideoWithCorrectId() throws Exception {

        Video video = new Video(1,"idyoutubevideo","video title", LocalDateTime.now(),"chanel title");
        VideoStatistics videoStatistics = new VideoStatistics(0,video,Instant.now(),1L,1L,1L);
        List<StatsInfoResponse> statsInfoResponse = List.of(new StatsInfoResponse(videoStatistics.getUpdateDate(), videoStatistics.getViewCount(),
                videoStatistics.getLikeCount(), videoStatistics.getCommentCount()));

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Instant.class, new InstantTypeAdapter())
                .create();

        String videoStatsResponse = gson.toJson(statsInfoResponse);

        when(videoStatisticsService.getAllStatsForAVideo("idyoutubevideo")).thenReturn(statsInfoResponse);

        RequestBuilder request = get("/videoStatistics/idyoutubevideo/all");
        MvcResult result = mvc.perform(request).andExpect(status().isOk()).andReturn();

        assertEquals(videoStatsResponse, result.getResponse().getContentAsString());
    }

    @Test
    void testGetTop20StatsForAVideoWithIncorrectId() throws Exception {

        when(videoStatisticsService.getTop20StatsForAVideo("idyoutubevideo")).thenThrow(VideoNotFoundException.class);

        RequestBuilder request = get("/videoStatistics/idyoutubevideo/top20");
        mvc.perform(request).andExpect(status().isNotFound()).andReturn();
    }

    @Test
    void testGetTop20StatsForAVideoWithCorrectId() throws Exception {

        Video video = new Video(1,"idyoutubevideo","video title", LocalDateTime.now(),"chanel title");
        VideoStatistics videoStatistics = new VideoStatistics(0,video,Instant.now(),1L,1L,1L);
        List<StatsInfoResponse> statsInfoResponse = List.of(new StatsInfoResponse(videoStatistics.getUpdateDate(), videoStatistics.getViewCount(),
                videoStatistics.getLikeCount(), videoStatistics.getCommentCount()));

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Instant.class, new InstantTypeAdapter())
                .create();

        String videoStatsResponse = gson.toJson(statsInfoResponse);

        when(videoStatisticsService.getTop20StatsForAVideo("idyoutubevideo")).thenReturn(statsInfoResponse);

        RequestBuilder request = get("/videoStatistics/idyoutubevideo/top20");
        MvcResult result = mvc.perform(request).andExpect(status().isOk()).andReturn();

        assertEquals(videoStatsResponse, result.getResponse().getContentAsString());
    }

}
