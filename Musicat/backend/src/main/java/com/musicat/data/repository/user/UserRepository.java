package com.musicat.data.repository.user;

import com.musicat.data.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {


  // 회원 전체 조회
  @Query("SELECT u FROM User u WHERE " +
      "(:nickname IS NULL OR LOWER(u.userNickname) LIKE LOWER(CONCAT('%', :nickname, '%'))) AND " +
      "(:isChattingBan IS NULL OR u.userIsChattingBan = :isChattingBan) AND " +
      "(:isBan IS NULL OR u.userIsBan = :isBan)")
  Page<User> findUsersByNicknameAndIsChattingBanAndIsBan(Optional<String> nickname,
      Optional<Boolean> isChattingBan, Optional<Boolean> isBan, Pageable pageable);

  Optional<User> findOneByUserId(String userId);

  // authority 권한을 가져오면서 user 객체 가져오기
  @Query("SELECT u FROM User u JOIN FETCH u.userAuthority WHERE u.userId = :userId")
  Optional<User> findByIdWithAuthorities(@Param("userId") String userId);

  @Query("SELECT u FROM User u JOIN FETCH u.userAuthority WHERE u.userSeq = :userSeq")
  Optional<User> findByUserSeqWithAuthorities(@Param("userSeq") long userSeq);


  // 카카오에서 제공한 ID로 회원이 이미 가입되어 있는지 확인
  boolean existsByUserId(String userId);

  // 카카오에서 제공한 ID로 회원이 이미 가입되어 있다면 회원 정보를 반환
  Optional<User> findByUserId(String userId);


}
