package com.bodyfit.restapi.model.dao;

import com.bodyfit.restapi.model.dto.Gym;
import com.bodyfit.restapi.model.dto.SearchCondition;

import java.util.List;
import java.util.Optional;

public interface GymDao {
    // 헬스장 등록
    void insertGym(Gym gym);
    // 헬스장 삭제
    void deleteGym(long gymId);
    //  헬스장 목록 가져오기
    List<Gym> selectAllGyms(double latitude, double longitude);

}
