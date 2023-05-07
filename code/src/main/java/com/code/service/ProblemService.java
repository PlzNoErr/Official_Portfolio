package com.code.service;

import com.code.data.dto.ProblemResponseDto;
import com.code.data.entity.Problem;
import com.code.data.entity.UserSubmitProblem;
import com.code.data.repository.ProblemRepository;
import com.code.data.repository.UserSubmitProblemRepository;
import com.code.util.builder.ProblemBuilderUtil;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.jni.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProblemService {
  // logger 정의
  private static final Logger logger = LoggerFactory.getLogger(ProblemService.class);
  // Repository 정의
  private final ProblemRepository problemRepository;
  private final UserSubmitProblemRepository userSubmitProblemRepository;
  // Util 정의
  private final ProblemBuilderUtil problemBuilderUtil;

  public ProblemResponseDto getProblem(long problemId) {
    logger.info("문제 조회");
    Problem problem = problemRepository.findById(problemId).orElseThrow(RuntimeException::new);
    return problemBuilderUtil.problemToProblemResponseDto(problem);
  }

  public long getUserSubmitProblem(long userSubmitProblemSeq) {
    logger.info("회원푼문제 조회");
    UserSubmitProblem userSubmitProblem = userSubmitProblemRepository.findById(userSubmitProblemSeq)
        .orElseThrow(RuntimeException::new);
    return userSubmitProblem.getProblemId();
  }
}
