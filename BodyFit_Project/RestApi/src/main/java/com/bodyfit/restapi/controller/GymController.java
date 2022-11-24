package com.bodyfit.restapi.controller;

import com.bodyfit.restapi.model.dto.Article;
import com.bodyfit.restapi.model.dto.Gym;
import com.bodyfit.restapi.model.dto.ListAndNavigation;
import com.bodyfit.restapi.model.dto.SearchCondition;
import com.bodyfit.restapi.model.service.GymService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/gyms")
@CrossOrigin(origins = {"*"}, methods = {RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.POST})
@ApiOperation("헬스장과 관련된 컨트롤러입니다.")
public class GymController {
    private final GymService gymService;

    @Autowired
    public GymController(GymService gymService) {
        this.gymService = gymService;
    }

    @PostMapping("")
    @ApiOperation("헬스장을 등록합니다. gymName, address, latitude, longitude가 필요합니다.")
    public ResponseEntity<Void> createGym(@RequestBody Gym gym) {
        try {
            gymService.registGym(gym);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{gymId}")
    @ApiOperation("헬스장을 삭제합니다. gymId가 필요합니다.")
    public ResponseEntity<Void> deleteGym(@PathVariable long gymId) {
        try {
            gymService.deleteGym(gymId);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/search")
    @ApiOperation("위도, 경도를 기준으로 대략 1km 반경안의 행스장들의 리스트를 가져옵니다.")
    public ResponseEntity<?> searchGym(Gym gym) {
        try {
            Optional<List<Gym>> list = gymService.getAllGyms(gym);
            if (list.isPresent() && list.get().size() > 0) {
                return new ResponseEntity<>(list, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
