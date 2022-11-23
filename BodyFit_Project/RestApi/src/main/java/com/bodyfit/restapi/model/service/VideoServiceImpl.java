package com.bodyfit.restapi.model.service;

import com.bodyfit.restapi.model.dao.VideoDao;
import com.bodyfit.restapi.model.dto.*;
import com.bodyfit.restapi.util.PageNavigation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class VideoServiceImpl implements VideoService {

    public final int VIDEO_PER_PAGE = 12;

    private VideoDao videoDao;

    @Autowired
    public VideoServiceImpl(VideoDao videoDao) {
        this.videoDao = videoDao;
    }

    @Override
    @Transactional
    public void createVideo(Video video) {
        videoDao.insertVideo(video);

    }

    @Override
    @Transactional
    public void updateVideo(Video video) {
        videoDao.updateVideo(video);
    }

    @Override
    @Transactional
    public void deleteVideo(long videoId) {
        videoDao.deleteVideo(videoId);
    }

    @Override
    @Transactional
    public Optional<Video> getVideoDetail(long videoId) {
        videoDao.updateViewCountByVideoId(videoId);
        return videoDao.selectByVideoId(videoId);
    }

    @Override
    public ListAndNavigation<Video> searchVideos(SearchCondition searchCondition) {
        searchCondition.setCountPerPage(VIDEO_PER_PAGE);
        PageNavigation pageNavigation = new PageNavigation(searchCondition.getCurrentPage(), videoDao.getTotalByCondition(searchCondition), VIDEO_PER_PAGE);
        List<Video> list = videoDao.selectAllByCondition(searchCondition);
        return new ListAndNavigation<>(list, pageNavigation);
    }

    @Override
    public ListAndNavigation<Video> likedVideos(long userSeq, int pageNum) {
        PageNavigation pageNavigation = new PageNavigation(pageNum, videoDao.getTotalByLike(userSeq), VIDEO_PER_PAGE);
        PageCondition pageCondition = PageCondition.builder().userSeq(userSeq).currentPage(pageNum).countPerPage(VIDEO_PER_PAGE).build();
        List<Video> list = videoDao.selectAllByLike(pageCondition);
        return new ListAndNavigation<>(list, pageNavigation);
    }

    @Override
    @Transactional
    public void likeOrDislike(long videoId, long userSeq) {
        Optional<Like> like = videoDao.selectLike(videoId, userSeq);
        if (like.isPresent()) {
            videoDao.updateLikeCountByVideoIdAndLong(videoId, -1L);
            videoDao.deleteLike(videoId, userSeq);
        } else {
            videoDao.updateLikeCountByVideoIdAndLong(videoId, 1L);
            videoDao.insertLike(videoId, userSeq);
        }
    }

    @Override
    public Optional<Like> isLiked(long videoId, long userSeq) {
        return videoDao.selectLike(videoId, userSeq);
    }

    @Override
    public ListAndNavigation<Video> followingLikeVideos(long userSeq, int pageNum) {
        PageNavigation pageNavigation = new PageNavigation(pageNum, videoDao.getTotalByFollowingLike(userSeq), VIDEO_PER_PAGE);
        PageCondition pageCondition = PageCondition.builder().userSeq(userSeq).currentPage(pageNum).countPerPage(VIDEO_PER_PAGE).build();
        List<Video> list = videoDao.selectAllByFollowingLike(pageCondition);
        return new ListAndNavigation<>(list, pageNavigation);
    }
}
