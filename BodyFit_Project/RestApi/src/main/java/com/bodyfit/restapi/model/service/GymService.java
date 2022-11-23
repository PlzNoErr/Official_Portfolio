package com.bodyfit.restapi.model.service;

import com.bodyfit.restapi.model.dto.Gym;

import java.util.List;
import java.util.Optional;

public interface GymService {
   // 헬스장 등록
    void registGym(Gym gym);

    // 헬스장 삭제
    void deleteGym(long gymId);

    // 게시글 Detail로 가기 위해 하나의 게시글 가져오기
    Optional<List<Gym>> getAllGyms(Gym gym);

}
