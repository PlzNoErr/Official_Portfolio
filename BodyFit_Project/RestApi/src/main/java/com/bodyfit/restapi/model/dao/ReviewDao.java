package com.bodyfit.restapi.model.dao;

import com.bodyfit.restapi.model.dto.*;

import java.util.List;
import java.util.Optional;

public interface ReviewDao {
    void insertReview(Review review);

    void updateReview(Review review);

    void updateReviewViewCount(long reviewId);

    void deleteReview(long reviewId);

    Optional<Review> selectReviewByReviewId(long reviewId);

    List<Review> selectReviewsBySearchCondition(SearchCondition searchCondition);

    List<Review> selectReviewsByUserSeq(SearchCondition searchCondition);

    int getTotalReviewBySearchCondition(SearchCondition searchCondition);

    int insertComment(ReviewComment reviewComment);

    void updateComment(ReviewComment reviewComment);

    int updateCommentsOrder(ReviewComment reviewComment);

    void deleteComment(long commentId);

    int getCommentOrder(ReviewComment originalReviewComment);

    Optional<ReviewComment> selectReviewCommentById(long commentId);

    List<ReviewComment> selectCommentsByReviewId(PageCondition pageCondition);

    int getTotalCommentByReviewId(long reviewId);

}
