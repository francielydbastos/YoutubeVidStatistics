package com.challenge.youtubeviews.response.video;

import lombok.Data;

@Data
public class ItensResponse {
    private String kind;
    private String etag;
    private String id;
    private SnippetResponse snippet;
}
