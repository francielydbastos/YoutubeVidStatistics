package com.challenge.youtubeviews.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@Entity
@Table(name = "video")
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int videoId;
    private String youtubeUrlId;
    private String videoTitle;
    private LocalDateTime uploadDate;
    private String chanelTitle;
}
