package com.challenge.youtubeviews.response.videostatistics;

import lombok.Data;

import java.util.List;

@Data
public class VideoStatisticsResponse {
    private String kind;
    private String etag;
    private List<ItensResponse> items;
}

