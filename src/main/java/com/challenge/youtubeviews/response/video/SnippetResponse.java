package com.challenge.youtubeviews.response.video;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SnippetResponse {
    private LocalDateTime publishedAt;
    private String title;
    private String channelTitle;
}
