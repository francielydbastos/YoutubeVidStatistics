package com.challenge.youtubeviews.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
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