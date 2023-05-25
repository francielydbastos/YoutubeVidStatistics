package com.challenge.youtubeviews.response.videostatistics;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItensResponse {
    private String kind;
    private String etag;
    private String id;
    private StatisticsResponse statistics;
}
