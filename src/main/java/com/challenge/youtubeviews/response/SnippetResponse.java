package com.challenge.youtubeviews.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SnippetResponse {
    private LocalDateTime publishedAt;
    private String title;
    private String channelTitle;
}
