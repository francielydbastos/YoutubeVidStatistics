package com.challenge.youtubeviews.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class VideoInfoResponse {
    private String videoTitle;
    private LocalDateTime uploadDate;
    private String chanelTitle;
    private long viewCount;
    private long likeCount;
    private long commentCount;
}
