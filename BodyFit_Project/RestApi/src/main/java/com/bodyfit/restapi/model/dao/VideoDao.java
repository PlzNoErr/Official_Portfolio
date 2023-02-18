package com.bodyfit.restapi.model.dao;

import com.bodyfit.restapi.model.dto.Like;
import com.bodyfit.restapi.model.dto.PageCondition;
import com.bodyfit.restapi.model.dto.SearchCondition;
import com.bodyfit.restapi.model.dto.Video;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

public interface VideoDao {
    void insertVideo(Video video);

    void updateVideo(Video video);

    void updateLikeCountByVideoIdAndLong(@Param("videoId") long videoId, @Param("i") long i);

    void updateReviewCountByVideoIdAndLong(@Param("videoId") long videoId, @Param("i") long i);

    void updateViewCountByVideoId(long videoId);

    void deleteVideo(long videoId);

    Optional<Video> selectByVideoId(long videoId);

    List<Video> selectAllByCondition(SearchCondition searchCondition);
    int getTotalByCondition(SearchCondition searchCondition);

    List<Video> selectAllByLike(PageCondition pageCondition);
    int getTotalByLike(long userSeq);

    void insertLike(@Param("videoId") long videoId, @Param("userSeq") long userSeq);

    void deleteLike(@Param("videoId") long videoId, @Param("userSeq") long userSeq);

    Optional<Like> selectLike(@Param("videoId") long videoId, @Param("userSeq") long userSeq);

    List<Video> selectAllByFollowingLike(PageCondition pageCondition);
    int getTotalByFollowingLike(long userSeq);
}
