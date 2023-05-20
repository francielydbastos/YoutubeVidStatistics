package com.challenge.youtubeviews.repository;

import com.challenge.youtubeviews.model.Video;
import com.challenge.youtubeviews.model.VideoStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VideoStatisticsRepository extends JpaRepository<VideoStatistics, Long> {
    Optional<VideoStatistics> findFirstByVideoIdOrderByVideoStatisticsIdDesc(Video videoId);

    List<VideoStatistics> findAllByVideoIdOrderByVideoStatisticsIdDesc(Video videoId);

    List<VideoStatistics> findTop20ByVideoIdOrderByVideoStatisticsIdDesc(Video videoId);
}
