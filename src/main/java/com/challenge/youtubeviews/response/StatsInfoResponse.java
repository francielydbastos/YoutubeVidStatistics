package com.challenge.youtubeviews.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class StatsInfoResponse {
    private Instant statsDate;
    private long viewCount;
    private long likeCount;
    private long commentCount;
}
