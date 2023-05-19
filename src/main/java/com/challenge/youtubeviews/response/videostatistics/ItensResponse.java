package com.challenge.youtubeviews.response.videostatistics;

import lombok.Data;

@Data
public class ItensResponse {
    private String kind;
    private String etag;
    private String id;
    private StatisticsResponse statistics;
}
