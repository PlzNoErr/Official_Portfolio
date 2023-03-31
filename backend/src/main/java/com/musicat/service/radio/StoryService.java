package com.musicat.service.radio;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.musicat.data.dto.story.StoryInfoDto;
import com.musicat.data.dto.story.StoryKafkaDto;
import com.musicat.data.dto.story.StoryRequestDto;
import com.musicat.data.entity.radio.Story;
import com.musicat.data.repository.radio.StoryRepository;
import com.musicat.service.kafka.KafkaProducerService;
import com.musicat.util.ConstantUtil;
import com.musicat.util.RegexUtil;
import com.musicat.util.builder.StoryBuilderUtil;
import java.util.Optional;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// Todo : 사연 Valid 검사 false일 경우 readed 처리 해줘야 한다. -> 이미 신청한 사연이 있는지 검증하기 위해서
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoryService {

  // logger 정의
  private static final Logger logger = LoggerFactory.getLogger(StoryService.class);

  // Utility 정의
  private final StoryBuilderUtil storyBuilderUtil;
  private final ConstantUtil constantUtil;

  // Service 정의
  private final KafkaProducerService kafkaProducerService;

  // Repository 정의
  private final StoryRepository storyRepository;

  // Util 정의
  private final RegexUtil regexUtil;


  /**
   * 사연 신청
   *
   * @param storyRequestDto
   */
  @Transactional
  public void insertStory(StoryRequestDto storyRequestDto) {

    Story story = storyRepository.save(
        storyBuilderUtil.buildStoryEntity(storyRequestDto));

    // 사연 데이터, 신청곡 를 카프카로 전송 -> 파이썬 서버에서 valid 체크 후 DB 반영, 인트로 음성 파일 생성, Reaction 음성 파일 생성, Outro 음성 파일 생성
    try {
      /**
       * 사연 Seq, 사용자Seq, 사연 제목, 사연 내용
       */
      StoryKafkaDto storyKafkaDto = storyBuilderUtil.buildStoryKafkaDto(story);


      logger.debug("사연 신청곡 [BEFORE]. 제목 : {}, 가수 : {}", storyKafkaDto.getStoryMusicTitle(), storyKafkaDto.getStoryMusicArtist());

      storyKafkaDto.setStoryMusicTitle(regexUtil.removeTextAfterSpecialChar(storyKafkaDto.getStoryMusicTitle()));
      storyKafkaDto.setStoryMusicArtist(regexUtil.removeTextAfterSpecialChar(storyKafkaDto.getStoryMusicArtist()));

      logger.debug("사연 신청곡 [AFTER]. 제목 : {}, 가수 : {}", storyKafkaDto.getStoryMusicTitle(), storyKafkaDto.getStoryMusicArtist());

      kafkaProducerService.send("verifyStory", storyKafkaDto);

    } catch (JsonProcessingException e) {
      System.err.println(e.getMessage());
      throw new RuntimeException("카프카 에러");
    }
  }

  /**
   * 사연 중복 검사
   *
   * @param userSeq
   * @return
   */
  public void isUniqueStory(long userSeq) {
    Optional<Story> optionalStory = storyRepository.findByUserSeqAndStoryReadedFalseOrStoryReadedNull(
        userSeq);

    if (optionalStory.isPresent()) {
      throw new EntityExistsException("중복 사연이 존재합니다.");
    }

  }


  /**
   * 사연 상세 조회
   *
   * @param storySeq
   * @return
   */
  public StoryInfoDto getStory(long storySeq) {
    Story story = storyRepository.findById(storySeq)
        .orElseThrow(EntityNotFoundException::new);

    return storyBuilderUtil.buildStoryInfoDto(story);
  }

  /**
   * 사연 삭제
   *
   * @param storySeq
   */
  @Transactional
  public void deleteStory(long storySeq) {

    Story story = storyRepository.findById(storySeq)
        .orElseThrow(() -> new EntityNotFoundException("사연이 존재하지 않습니다.")); // 사연 조회

    storyRepository.delete(story); // 사연 삭제
  }

}
