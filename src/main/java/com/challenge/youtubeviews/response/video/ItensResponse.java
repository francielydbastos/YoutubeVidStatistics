package com.challenge.youtubeviews.response.video;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItensResponse {
    private String kind;
    private String etag;
    private String id;
    private SnippetResponse snippet;
}
