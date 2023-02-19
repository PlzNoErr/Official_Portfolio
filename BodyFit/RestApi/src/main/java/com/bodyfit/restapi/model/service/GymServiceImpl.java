package com.bodyfit.restapi.model.service;

import com.bodyfit.restapi.model.dao.GymDao;
import com.bodyfit.restapi.model.dto.Article;
import com.bodyfit.restapi.model.dto.Gym;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GymServiceImpl implements GymService{
    private final GymDao gymDao;
    @Autowired
    GymServiceImpl(GymDao gymDao) {
        this.gymDao = gymDao;
    }
    @Override
    public void registGym(Gym gym) {
        gymDao.insertGym(gym);
    }

    @Override
    public void deleteGym(long gymId) {
        gymDao.deleteGym(gymId);
    }

    @Override
    public Optional<List<Gym>> getAllGyms(Gym gym) {
        double latitude = gym.getLatitude();
        double longitude = gym.getLongitude();
        return Optional.ofNullable(gymDao.selectAllGyms(latitude, longitude));
    }
}
