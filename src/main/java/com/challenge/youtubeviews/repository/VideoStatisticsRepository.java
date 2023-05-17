package com.challenge.youtubeviews.repository;

import com.challenge.youtubeviews.model.VideoStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoStatisticsRepository extends JpaRepository<VideoStatistics, Long> {
}
