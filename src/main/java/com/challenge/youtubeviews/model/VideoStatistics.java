package com.challenge.youtubeviews.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Data
@Entity
@Table(name = "video_statistics")
public class VideoStatistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long videoStatisticsId;
    @ManyToOne
    @JoinColumn(name = "video_id")
    private Video videoId;
    private Instant updateDate;
    private long viewCount;
    private long likeCount;
    private long commentCount;
}