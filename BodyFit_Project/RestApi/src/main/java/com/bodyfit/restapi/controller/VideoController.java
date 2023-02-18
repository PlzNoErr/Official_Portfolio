package com.bodyfit.restapi.controller;

import com.bodyfit.restapi.model.dto.*;
import com.bodyfit.restapi.model.service.VideoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/videos")
@CrossOrigin(origins = {"*"}, methods = {RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.POST})
@ApiOperation("영상과 관련된 컨트롤러입니다.")
public class VideoController {

    private VideoService videoService;

    @Autowired
    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }


    @PostMapping("")
    @ApiOperation("영상을 등록합니다. videoUrl, title, channel, description, category가 필요합니다.")
    public ResponseEntity<Void> createVideo(@RequestBody Video video) {
        try {
            videoService.createVideo(video);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("")
    @ApiOperation("영상을 수정합니다.")
    public ResponseEntity<Void> updateVideo(@RequestBody Video video) {
        try {
            videoService.updateVideo(video);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{videoId}")
    @ApiOperation("영상을 삭제합니다.")
    public ResponseEntity<Void> deleteVideo(@PathVariable long videoId) {
        try {
            videoService.deleteVideo(videoId);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{videoId}")
    @ApiOperation("videoId로 영상을 하나 가져옵니다.")
    public ResponseEntity<?> getVideoDetail(@PathVariable long videoId) {
        try {
            Optional<Video> video = videoService.getVideoDetail(videoId);
            if (video.isPresent()) {
                return new ResponseEntity<>(video.get(), HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/search")
    @ApiOperation("조건에 맞는 영상들을 가져옵니다. mod는 category, title, description, channel이 있습니다.")
    public ResponseEntity<?> searchVideos(SearchCondition searchCondition) {
        try {
            ListAndNavigation<Video> videoList = videoService.searchVideos(searchCondition);
            if (videoList.getList() != null && videoList.getList().size() > 0) {
                return new ResponseEntity<>(videoList, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/like/{userSeq}/{pageNum}")
    @ApiOperation("userSeq의 유저가 좋아요를 누른 영상들을 가져옵니다.")
    public ResponseEntity<?> likedVideos(@PathVariable long userSeq, @PathVariable int pageNum) {
        try {
            ListAndNavigation<Video> videoList = videoService.likedVideos(userSeq, pageNum);
            if (videoList.getList() != null && videoList.getList().size() > 0) {
                return new ResponseEntity<>(videoList, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/like")
    @ApiOperation("좋아요를 등록하거나 제거합니다.")
    public ResponseEntity<Void> likeOrDislike(@RequestBody Like like) {
        try {
            videoService.likeOrDislike(like.getVideoId(), like.getUserSeq());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{videoId}/like/{userSeq}")
    @ApiOperation("특정 회원이 이 영상을 좋아요했는지 가져옵니다.")
    public ResponseEntity<?> isLiked(@PathVariable long videoId, @PathVariable long userSeq) {
        try {
            Optional<Like> like = videoService.isLiked(videoId, userSeq);
            if (like.isPresent()) {
                return new ResponseEntity<>(like, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{userSeq}/follow/{pageNum}")
    @ApiOperation("팔로우한 회원이 좋아요를 찍은 영상 리스트를 모두 가져온다.")
    public ResponseEntity<?> followingLikeVideos(@PathVariable long userSeq, @PathVariable int pageNum) {
        try {
            ListAndNavigation<Video> videoList = videoService.likedVideos(userSeq, pageNum);
            if (videoList.getList() != null && videoList.getList().size() > 0) {
                return new ResponseEntity<>(videoList, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
