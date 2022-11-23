package com.bodyfit.restapi.model.service;

import com.bodyfit.restapi.model.dto.ListAndNavigation;
import com.bodyfit.restapi.model.dto.Review;
import com.bodyfit.restapi.model.dto.ReviewComment;
import com.bodyfit.restapi.model.dto.SearchCondition;

import java.util.List;
import java.util.Optional;

public interface ReviewService {
    void createReview(Review review);

    void updateReview(Review review);

    void deleteReview(long reviewId);

    Optional<Review> getReview(long reviewId);

    ListAndNavigation<Review> ReviewsByUserSeq(SearchCondition searchCondition);

    ListAndNavigation<Review> reviewsBySearchCondition(SearchCondition searchCondition);

    void createComment(ReviewComment reviewComment);

    void updateComment(ReviewComment reviewComment);

    void deleteComment(long commentId);

    ListAndNavigation<ReviewComment> commentsByReviewId(long reviewId, int pageNum);

    ListAndNavigation<ReviewComment> commentsByReviewId(long reviewId);
}
