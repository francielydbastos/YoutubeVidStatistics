package com.challenge.youtubeviews.response.videostatistics;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StatisticsResponse {
    private long viewCount;
    private long likeCount;
    private long commentCount;
}
