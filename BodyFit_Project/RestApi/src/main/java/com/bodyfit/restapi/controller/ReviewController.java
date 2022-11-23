package com.bodyfit.restapi.controller;

import com.bodyfit.restapi.model.dto.ListAndNavigation;
import com.bodyfit.restapi.model.dto.Review;
import com.bodyfit.restapi.model.dto.ReviewComment;
import com.bodyfit.restapi.model.dto.SearchCondition;
import com.bodyfit.restapi.model.service.ReviewService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin(origins = {"*"}, methods = {RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.POST})
@ApiOperation("영상 리뷰와 댓글에 관련된 컨트롤러입니다.")
public class ReviewController {

    private ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("")
    @ApiOperation("리뷰를 등록합니다. videoId, userSeq, title, content가 필요합니다.")
    public ResponseEntity<Void> createReview(@RequestBody Review review) {
        try {
            reviewService.createReview(review);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("")
    @ApiOperation("리뷰를 수정합니다.")
    public ResponseEntity<Void> updateReview(@RequestBody Review review) {
        try {
            reviewService.updateReview(review);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{reviewId}")
    @ApiOperation("리뷰를 삭제합니다.")
    public ResponseEntity<Void> deleteReview(@PathVariable long reviewId) {
        try {
            reviewService.deleteReview(reviewId);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{reviewId}")
    @ApiOperation("리뷰를 하나 가져옵니다.")
    public ResponseEntity<?> getReview(@PathVariable long reviewId) {
        try {
            Optional<Review> review = reviewService.getReview(reviewId);
            if (review.isPresent()) {
                return new ResponseEntity<>(review.get(), HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/myvideo/{userSeq}/{pageNum}")
    @ApiOperation("내가 작성한 리뷰를 가져옵니다.")
    public ResponseEntity<?> getReviewsByUserSeq(@PathVariable long userSeq, @PathVariable int pageNum) {
        try {
            SearchCondition searchCondition = SearchCondition.builder().keyword(String.valueOf(userSeq)).currentPage(pageNum).build();
            ListAndNavigation<Review> reviews = reviewService.ReviewsByUserSeq(searchCondition);
            if (reviews.getList() != null && reviews.getList().size() > 0) {
                return new ResponseEntity<>(reviews, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/video/{videoId}/{pageNum}")
    @ApiOperation("영상의 리뷰를 가져옵니다.")
    public ResponseEntity<?> getReviewsByVideoId(@PathVariable long videoId, @PathVariable int pageNum) {
        try {
            SearchCondition searchCondition = SearchCondition.builder().keyword(String.valueOf(videoId)).currentPage(pageNum).build();
            ListAndNavigation<Review> reviews = reviewService.reviewsBySearchCondition(searchCondition);
            if (reviews.getList() != null && reviews.getList().size() > 0) {
                return new ResponseEntity<>(reviews, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/search")
    @ApiOperation("영상의 리뷰를 검색합니다.")
    public ResponseEntity<?> searchReviews(SearchCondition searchCondition) {
        try {
            ListAndNavigation<Review> reviews = reviewService.reviewsBySearchCondition(searchCondition);
            if (reviews.getList() != null && reviews.getList().size() > 0) {
                return new ResponseEntity<>(reviews, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{reviewId}/comments")
    @ApiOperation("리뷰 댓글을 작성합니다. content, originalCommentId, reviewId, userSeq 필요함")
    public ResponseEntity<Void> createReviewComment(@RequestBody ReviewComment reviewComment) {
        try {
            reviewService.createComment(reviewComment);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{reviewId}/comments")
    @ApiOperation("리뷰 댓글을 수정합니다.")
    public ResponseEntity<Void> updateReviewComment(@RequestBody ReviewComment reviewComment) {
        try {
            reviewService.updateComment(reviewComment);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{reviewId}/comments/{commentId}")
    @ApiOperation("리뷰 댓글을 삭제합니다. 사실 지우는 것이 아니라 삭제됨(deleted)로 처리함")
    public ResponseEntity<Void> deleteComment(@PathVariable long commentId) {
        try {
            reviewService.deleteComment(commentId);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{reviewId}/comments/{pageNum}")
    @ApiOperation("리뷰의 댓글을 가져옵니다.")
    public ResponseEntity<?> getCommentsByReviewId(@PathVariable long reviewId, @PathVariable int pageNum) {
        try {
            ListAndNavigation<ReviewComment> comments = reviewService.commentsByReviewId(reviewId, pageNum);
            if (comments.getList() != null && comments.getList().size() > 0) {
                return new ResponseEntity<>(comments, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{reviewId}/comments")
    @ApiOperation("리뷰의 댓글을 가져옵니다.")
    public ResponseEntity<?> getRecentCommentsByReviewId(@PathVariable long reviewId) {
        try {
            ListAndNavigation<ReviewComment> comments = reviewService.commentsByReviewId(reviewId);
            if (comments.getList() != null && comments.getList().size() > 0) {
                return new ResponseEntity<>(comments, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
