package com.challenge.youtubeviews.response;

import lombok.Data;

@Data
public class ItensResponse {
    private String kind;
    private String etag;
    private String id;
    private SnippetResponse snippet;
}
