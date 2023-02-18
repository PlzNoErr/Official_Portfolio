package com.bodyfit.restapi.model.service;

import com.bodyfit.restapi.model.dto.Follow;
import com.bodyfit.restapi.model.dto.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
        // 회원가입
        void singupUser(User user);

        // 회원탈퇴
        void deactivateUser(String userId);

        // 회원정보 수정
        void updateUserInfo(User user);

        // 회원정보 하나만 가져오기 (id 활용)
        User getUserById(String userId);

        // 로그인 검증 (id, password 활용)
        Optional<User> checkUserForLogin(String userId, String password);

        // 종복 회원 검증 (id 활용)
        Optional<User> checkForReduplicationCheckById(String userId);

        // 종복 회원 검증 (nickname 활용)
        Optional<User> checkForReduplicationCheckByNickname(String nickName);

        // 조건에 맞는 회원정보 가져오기


        // 팔로우
        void addFollow(long followingSeq, long followedSeq);

        // 언팔로우
        void unFollow(long followingSeq, long followedSeq);

        // 유저 테이블 안의 팔로우 수의 변동
        void changeFollowedCount(long UserSeq, long change);

        // 유저 테이블 안의 팔로잉 수의 변동
        void changeFollowingCount(long UserSeq, long change);

        // 내가 팔로우한 모든 유저들 가져오기
        List<User> getAllFollowersByUserSeq(long userSeq);
        // 나를 팔로우한 모든 유저들 가져오기
        List<User> getAllFollowedByUserSeq(long userSeq);

        // 유저가 팔로우 중인지 확인하기
        Follow checkFollowingNow(Follow follow);

}
