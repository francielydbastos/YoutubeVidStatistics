package com.challenge.youtubeviews.repository;

import com.challenge.youtubeviews.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VideoRepository extends JpaRepository<Video, Integer> {
    Optional<Video> findByYoutubeUrlId(String youtubeUrlId);
}
