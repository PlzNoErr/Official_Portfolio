package com.bodyfit.restapi.model.service;

import com.bodyfit.restapi.model.dao.ReviewDao;
import com.bodyfit.restapi.model.dao.VideoDao;
import com.bodyfit.restapi.model.dto.*;
import com.bodyfit.restapi.util.PageNavigation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {

    public final int REVIEWS_PER_PAGE = 10;
    public final int COMMENTS_PER_PAGE = 20;

    private ReviewDao reviewDao;
    private VideoDao videoDao;

    @Autowired
    public ReviewServiceImpl(ReviewDao reviewDao, VideoDao videoDao) {
        this.reviewDao = reviewDao;
        this.videoDao = videoDao;
    }

    @Override
    public void createReview(Review review) {
        videoDao.updateReviewCountByVideoIdAndLong(review.getVideoId(), 1L);
        reviewDao.insertReview(review);
    }

    @Override
    public void updateReview(Review review) {
        reviewDao.updateReview(review);
    }

    @Override
    public void deleteReview(long reviewId) {
        Review review = reviewDao.selectReviewByReviewId(reviewId).get();
        videoDao.updateReviewCountByVideoIdAndLong(review.getVideoId(), -1);
        reviewDao.deleteReview(reviewId);
    }

    @Override
    public Optional<Review> getReview(long reviewId) {
        reviewDao.updateReviewViewCount(reviewId);
        return reviewDao.selectReviewByReviewId(reviewId);
    }

    @Override
    public ListAndNavigation<Review> ReviewsByUserSeq(SearchCondition searchCondition) {
        searchCondition.setCountPerPage(REVIEWS_PER_PAGE);
        PageNavigation pageNavigation = new PageNavigation(searchCondition.getCurrentPage(), reviewDao.getTotalReviewBySearchCondition(searchCondition), REVIEWS_PER_PAGE);
        List<Review> list = reviewDao.selectReviewsByUserSeq(searchCondition);
        return new ListAndNavigation<>(list, pageNavigation);
    }

    @Override
    public ListAndNavigation<Review> reviewsBySearchCondition(SearchCondition searchCondition) {
        searchCondition.setCountPerPage(REVIEWS_PER_PAGE);
        PageNavigation pageNavigation = new PageNavigation(searchCondition.getCurrentPage(), reviewDao.getTotalReviewBySearchCondition(searchCondition), REVIEWS_PER_PAGE);
        List<Review> list = reviewDao.selectReviewsBySearchCondition(searchCondition);
        return new ListAndNavigation<>(list, pageNavigation);
    }

    /**
     * content, originalCommentId, reviewId, userSeq ?????????
     * originalCommentId??? ????????? = 0?????? ???????????? ???????????? ?????????????????? ??????????????? ???????????? ??????
     * originalCommentId??? ????????? = ???????????? ?????? ?????? ???????????????
     * - groupId => ????????????=????????????????????? ???????????? ?????????????????? ????????????
     * - groupOrder ?????????
     * - - IFNULL(??????????????? ????????? ???????????? ????????? ???????????? ?????? ??????)
     * - - IFNULL(??????????????? ???????????? ????????? ??? ?????? ????????? ??????) + 1
     * - - (?????? ???????????? ????????? ???????????? ????????? ??????) + 1
     * - depth => ??????????????? ?????? + 1
     * - ???????????? ???????????? ??? ????????? ??????????????? ????????? ????????? ?????????
     * - ??????
     *
     * @param reviewComment
     */
    @Override
    public void createComment(ReviewComment reviewComment) {
        if (reviewComment.getOriginalCommentId() == 0L) {
            reviewDao.insertComment(reviewComment);
            reviewComment.setGroupId(reviewComment.getCommentId());
            reviewDao.updateComment(reviewComment);
        } else {
            Optional<ReviewComment> originalComment = reviewDao.selectReviewCommentById(reviewComment.getOriginalCommentId());
            if (originalComment.isPresent()) {
                reviewComment.setGroupId(originalComment.get().getGroupId());
                reviewComment.setGroupOrder(reviewDao.getCommentOrder(originalComment.get()));
                reviewComment.setDepth(originalComment.get().getDepth() + 1);
                int a = reviewDao.updateCommentsOrder(reviewComment);
                reviewDao.insertComment(reviewComment);
            }
        }
    }

    @Override
    public void updateComment(ReviewComment reviewComment) {
        reviewDao.updateComment(reviewComment);
    }

    @Override
    public void deleteComment(long commentId) {
        reviewDao.deleteComment(commentId);
    }

    @Override
    public ListAndNavigation<ReviewComment> commentsByReviewId(long reviewId, int pageNum) {
        PageNavigation pageNavigation = new PageNavigation(pageNum, reviewDao.getTotalCommentByReviewId(reviewId), COMMENTS_PER_PAGE);
        PageCondition pageCondition = PageCondition.builder().reviewId(reviewId).currentPage(pageNum).countPerPage(COMMENTS_PER_PAGE).build();
        List<ReviewComment> list = reviewDao.selectCommentsByReviewId(pageCondition);
        return new ListAndNavigation<>(list, pageNavigation);
    }
    @Override
    public ListAndNavigation<ReviewComment> commentsByReviewId(long reviewId) {
        PageNavigation pageNavigation = new PageNavigation(reviewDao.getTotalCommentByReviewId(reviewId), COMMENTS_PER_PAGE);
        PageCondition pageCondition = PageCondition.builder().reviewId(reviewId).currentPage(pageNavigation.getCurrentPage()).countPerPage(COMMENTS_PER_PAGE).build();
        List<ReviewComment> list = reviewDao.selectCommentsByReviewId(pageCondition);
        return new ListAndNavigation<>(list, pageNavigation);
    }
}