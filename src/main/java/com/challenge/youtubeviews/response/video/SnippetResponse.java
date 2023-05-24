package com.challenge.youtubeviews.response.video;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class SnippetResponse {
    private LocalDateTime publishedAt;
    private String title;
    private String channelTitle;
}
