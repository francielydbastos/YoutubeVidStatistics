package com.challenge.youtubeviews.response.video;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class VideoResponse {
    private String kind;
    private String etag;
    private List<ItensResponse> items;
}

