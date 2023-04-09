package com.musicat.service.chat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import javax.annotation.PostConstruct;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PerspectiveService {

  @Value("${perspective.api-key}")
  private String API_KEY;
  private String ENDPOINT;
  private static final String PREFIX_TEXT = "content: ";
  private static final double FILTER_SCORE = 0.5;

  @PostConstruct
  private void init() {
    ENDPOINT = "https://commentanalyzer.googleapis.com/v1alpha1/comments:analyze?key=" + API_KEY;
  }

  public boolean filterText(String text) {
    try {
      long beforeTime = System.currentTimeMillis();
      // bad word filter API 내부적으로 숫자타입을 못받는 문제점 존재. ex) 1텍스트 -> 400 에러 발생함
      // 헤결하기 위해서 text 앞부분에 String 타입 내용 강제적으로 추가
      String requestBody = "{\"comment\": {\"text\":\"" + PREFIX_TEXT + text
          + "\"}, \"requestedAttributes\": {\"TOXICITY\":{}}}";
      String response = sendRequest(requestBody, ENDPOINT);

      JSONObject jsonResponse = new JSONObject(response);
      double summaryScoreValue = jsonResponse.getJSONObject("attributeScores")
          .getJSONObject("TOXICITY")
          .getJSONObject("summaryScore")
          .getDouble("value");

      System.out.println("Summary Score Value: " + summaryScoreValue);

      long afterTime = System.currentTimeMillis();
      System.out.println("시간 차이 : " + (afterTime - beforeTime));

        if (summaryScoreValue >= FILTER_SCORE) {
            return false; // 공격적인 채팅
        }

    } catch (Exception e) {
      throw new RuntimeException("비속어 필터링 관련 에러 발생");
    }

    return true; // 클린 채팅
  }

  private String sendRequest(String requestBody, String endpoint) throws IOException {
    URL url = new URL(endpoint);
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("POST");
    conn.setRequestProperty("Content-Type", "application/json");
    conn.setDoOutput(true);

    try (OutputStream os = conn.getOutputStream()) {
      os.write(requestBody.getBytes());
      os.flush();
    }

    int responseCode = conn.getResponseCode();
    System.out.println("Response Code : " + responseCode);

    String response;
    try (InputStream inputStream = conn.getInputStream()) {
      response = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
    }

    conn.disconnect();

    return response;
  }

}
