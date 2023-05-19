package com.challenge.youtubeviews.response.videostatistics;

import lombok.Data;

@Data
public class StatisticsResponse {
    private long viewCount;
    private long likeCount;
    private long commentCount;
}
