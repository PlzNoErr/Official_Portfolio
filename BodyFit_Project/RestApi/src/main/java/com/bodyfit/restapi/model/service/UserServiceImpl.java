package com.bodyfit.restapi.model.service;

import com.bodyfit.restapi.model.dao.UserDao;
import com.bodyfit.restapi.model.dto.Follow;
import com.bodyfit.restapi.model.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    private final UserDao userDao;
    @Autowired
    UserServiceImpl(UserDao userDao){
        this.userDao = userDao;
    }

    @Override
    public void singupUser(User user) {
        userDao.insertUser(user);
    }

    @Override
    public void deactivateUser(String userId) {
        userDao.deleteUser(userId);

    }

    @Override
    public void updateUserInfo(User user) {
        userDao.updateUser(user);
    }

    @Override
    public User getUserById(String userId) {
        return userDao.selectUserById(userId);
    }

    @Override
    public Optional<User> checkUserForLogin(String userId, String password) {
        return userDao.seletUserByIdandPassword(userId, password);
    }

    @Override
    public Optional<User> checkForReduplicationCheckById(String userId) {
        return userDao.seletUserForReduplicationCheckById(userId);
    }

    @Override
    public Optional<User> checkForReduplicationCheckByNickname(String nickName) {
        return userDao.seletUserForReduplicationCheckByNickname(nickName);
    }
    @Override
    public void addFollow(long followingSeq, long followedSeq) {
        userDao.insertFollow(followingSeq, followedSeq);
    }

    @Override
    public void unFollow(long followingSeq, long followedSeq) {
        userDao.deleteFollow(followingSeq, followedSeq);
    }

    @Override
    public void changeFollowedCount(long UserSeq, long change) {
        userDao.updateFollowedCount(UserSeq, change);
    }

    @Override
    public void changeFollowingCount(long UserSeq, long change) {
        userDao.updateFollowingCount(UserSeq, change);
    }

    @Override
    public List<User> getAllFollowersByUserSeq(long userSeq) {
        return userDao.selectAllFollowersByUserSeq(userSeq);
    }

    @Override
    public List<User> getAllFollowedByUserSeq(long userSeq) {
        return userDao.selectAllFollowedByUserSeq(userSeq);
    }

    @Override
    public Follow checkFollowingNow(Follow follow) {
        return userDao.selectFollowingNow(follow);
    }
}
