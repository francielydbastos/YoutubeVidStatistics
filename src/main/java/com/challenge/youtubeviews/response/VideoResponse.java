package com.challenge.youtubeviews.response;

import lombok.Data;

import java.util.List;

@Data
public class VideoResponse {
    private String kind;
    private String etag;
    private List<ItensResponse> items;
}

