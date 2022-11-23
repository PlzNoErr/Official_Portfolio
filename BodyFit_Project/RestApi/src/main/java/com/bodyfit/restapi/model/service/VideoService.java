package com.bodyfit.restapi.model.service;

import com.bodyfit.restapi.model.dto.*;

import java.util.List;
import java.util.Optional;

public interface VideoService {
    void createVideo(Video video);

    void updateVideo(Video video);

    void deleteVideo(long videoId);

    Optional<Video> getVideoDetail(long videoId);

    ListAndNavigation<Video> searchVideos(SearchCondition searchCondition);

    ListAndNavigation<Video> likedVideos(long userSeq, int pageNum);

    void likeOrDislike(long videoId, long userSeq);

    Optional<Like> isLiked(long videoId, long userSeq);

    ListAndNavigation<Video> followingLikeVideos(long userSeq, int pageNum);
}
