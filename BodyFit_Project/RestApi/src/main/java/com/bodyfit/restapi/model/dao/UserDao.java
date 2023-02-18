package com.bodyfit.restapi.model.dao;

import com.bodyfit.restapi.model.dto.Follow;
import com.bodyfit.restapi.model.dto.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    // 회원가입
    void insertUser(User user);

    // 회원탈퇴
    void deleteUser(String userId);

    // 회원정보 수정
    void updateUser(User user);

    // 회원정보 하나만 가져오기 (id 활용)
    User selectUserById(String userId);

    //  login검증
    Optional<User> seletUserByIdandPassword(String userId, String password);

    // 회원가입 중복 검증을 위해 아이디 가져오기
    Optional<User> seletUserForReduplicationCheckById(String userId);

    // 회원가입 중복 검증을 위해 닉네임 가져오기
    Optional<User> seletUserForReduplicationCheckByNickname(String nickName);


    // 팔로우
    void insertFollow(@Param("followingSeq") long followingSeq, @Param("followedSeq") long followedSeq);

    // 언팔로우
    void deleteFollow(@Param("followingSeq") long followingSeq, @Param("followedSeq") long followedSeq);

    // 유저 테이블 안의 팔로우 수의 변동
    void updateFollowedCount(long UserSeq, long change);

    // 유저 테이블 안의 팔로잉 수의 변동
    void updateFollowingCount(long UserSeq, long change);

    // 내가 팔로우한 모든 유저들 가져오기
    List<User> selectAllFollowersByUserSeq(long userSeq);

    // 나를 팔로우한 모든 유저들 가져오기
    List<User> selectAllFollowedByUserSeq(long userSeq);

    // 요청한 유저가 팔로우 중인지 확인한다.
    Follow selectFollowingNow(Follow follow);


}
