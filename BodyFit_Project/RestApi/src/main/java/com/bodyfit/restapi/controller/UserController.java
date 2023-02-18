package com.bodyfit.restapi.controller;

import com.bodyfit.restapi.model.dto.Follow;
import com.bodyfit.restapi.model.dto.User;
import com.bodyfit.restapi.model.service.UserService;
import com.bodyfit.restapi.security.JwtTokenProvider;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@ApiOperation("유저와 관련된 컨트롤러입니다.")
public class UserController {
    private final UserService userService;
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    public UserController(UserService userService, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping()
    @ApiOperation(value = "회원가입 절차를 실행합니다. userId, password, email, nickName이 필요합니다.")
    public ResponseEntity<?> registNewUser(@RequestBody User user) {
        try {
            userService.singupUser(user);
            return new ResponseEntity<Integer>(HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("{userId}")
    @ApiOperation(value = "{userId}의 회원탈퇴 절차를 실행합니다.")
    public ResponseEntity<?> deactivateUser(@PathVariable String userId) {
        try {
            userService.deactivateUser(userId);
            return new ResponseEntity<Integer>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping()
    @ApiOperation(value = "회원 정보를 수정합니다. password, email, nickName만 수정이 가능합니다.")
    public ResponseEntity<?> changeUser(@RequestBody User user) {
        try {
            userService.updateUserInfo(user);
            return new ResponseEntity<Integer>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/checksignup")
    @ApiOperation(value = "회원가입의 중복 여부를 확인합니다. userId와 nickName 2가지를 검증합니다")
    public ResponseEntity<?> checkReduplication(@RequestBody User user) {
        Map<String, Object> result = new HashMap<>();
        try {
            Optional<User> infoId = userService.checkForReduplicationCheckById(user.getUserId());
            Optional<User> infoNickname = userService.checkForReduplicationCheckByNickname(user.getNickName());
            if (infoId.isPresent()) {
                result.put("userId", infoId.get().getUserId());
            } else {
                result.put("userId", "");
            }
            if (infoNickname.isPresent()) {
                result.put("nickName", infoNickname.get().getNickName());
            } else {
                result.put("nickName", "");
            }
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    @ApiOperation(value = "입력한 ID와 Password가 회원인지 확인하는 로그인 절차를 진행합니다.")
    public ResponseEntity<?> login(@RequestBody User user) {
        Map<String, Object> result = new HashMap<>();
        HttpStatus status = null;
        try {
            Optional<User> info = userService.checkUserForLogin(user.getUserId(), user.getPassword());
            if (info.isPresent()) {
                result.put("access-token", jwtTokenProvider.createToken(info.get()));
                result.put("userSeq", info.get().getUserSeq());
                result.put("userId", info.get().getUserId());
                result.put("nickName", info.get().getNickName());
                result.put("email", info.get().getEmail());
                status = HttpStatus.ACCEPTED;
            } else {
                result.put("message", "FAIL");
                status = HttpStatus.NO_CONTENT;
            }
        } catch (Exception e) {
            result.put("message", "SERVER_ERROR");
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(result, status);
    }

    @GetMapping("/followlist/{userSeq}")
    @ApiOperation(value = "내가 팔로우/나를 팔로우한 유저의 명단을 가져옵니다.")
    public ResponseEntity<?> getFollowlist(@PathVariable long userSeq) {
        try {
            List<User> followinglist = userService.getAllFollowersByUserSeq(userSeq);
            List<User> followedlist = userService.getAllFollowedByUserSeq(userSeq);
            Map<String, List<User>> followlist = new HashMap<>();
            followlist.put("followinglist", followinglist);
            followlist.put("followedlist", followedlist);
            return new ResponseEntity<Map>(followlist, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/follow")
    @ApiOperation(value = "팔로우를 실행합니다.")
    public ResponseEntity<?> addFollow(@RequestBody Follow follow) {
        try {
            userService.addFollow(follow.getFollowingSeq(), follow.getFollowedSeq());
            userService.changeFollowedCount(follow.getFollowingSeq(), 1);
            userService.changeFollowingCount(follow.getFollowingSeq(), 1);
            return new ResponseEntity<Integer>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/follow")
    @ApiOperation(value = "언팔로우를 실행합니다.")
    public ResponseEntity<?> unFollow(@RequestBody Follow follow) {
        try {
            userService.unFollow(follow.getFollowingSeq(), follow.getFollowedSeq());
            userService.changeFollowedCount(follow.getFollowingSeq(), -1);
            userService.changeFollowingCount(follow.getFollowingSeq(), -1);
            return new ResponseEntity<Integer>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/checkfollow")
    @ApiOperation(value = "해당 유저를 팔로우 중인지 확인해 줍니다.")
    public ResponseEntity<?> checkFollow(@RequestBody Follow follow) {
        try {
            Follow info = userService.checkFollowingNow(follow);
            if (info != null) return new ResponseEntity<Integer>(HttpStatus.OK);
            else return new ResponseEntity<Integer>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/exception")
    @ApiOperation("인증 실패")
    public ResponseEntity<Void> exceptionHandle(){
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

}
