package com.challenge.youtubeviews.response.videostatistics;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoStatisticsResponse {
    private String kind;
    private String etag;
    private List<ItensResponse> items;
}

