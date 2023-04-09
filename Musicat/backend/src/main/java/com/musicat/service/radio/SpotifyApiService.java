package com.musicat.service.radio;

import com.musicat.data.dto.spotify.SpotifySearchResultDto;
import com.musicat.data.dto.spotify.*;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class SpotifyApiService {

  @Value("${spotify.client.id}")
  private String clientId;

  @Value("${spotify.client.secret}")
  private String clientSecret;

  /**
   * 엑세스 토큰 불러오기
   *
   * @return
   */
  private String getAccessToken() {
    RestTemplate restTemplate = new RestTemplate();

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    headers.setBasicAuth(clientId, clientSecret);

    MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
    body.add("grant_type", "client_credentials");

    HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

    ResponseEntity<AccessTokenResponse> responseEntity = restTemplate.exchange(
        "https://accounts.spotify.com/api/token",
        HttpMethod.POST,
        requestEntity,
        AccessTokenResponse.class);

    return responseEntity.getBody().getAccess_token();
  }

  public List<SpotifySearchResultDto> searchSpotifyMusicList(String query) {
    String accessToken = getAccessToken();
    RestTemplate restTemplate = new RestTemplate();

    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(accessToken);

    HttpEntity<String> requestEntity = new HttpEntity<>(headers);

    String encodedQuery;
    try {
      encodedQuery = URLEncoder.encode(query, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException("Failed to encode the query string.", e);
    }

    ResponseEntity<SearchResponse> responseEntity = restTemplate.exchange(
        "https://api.spotify.com/v1/search?q={query}&type=track&limit=10&market=KR&locale=ko-KR",
        HttpMethod.GET,
        requestEntity,
        SearchResponse.class,
        query);
    ResponseEntity<SearchResponse> responseEntityEn = restTemplate.exchange(
        "https://api.spotify.com/v1/search?q={query}&type=track&limit=10&market=KR&",
        HttpMethod.GET,
        requestEntity,
        SearchResponse.class,
        query);

    List<SpotifySearchResultDto> results = new ArrayList<>();
    int resultSize = responseEntity.getBody().getTracks().getItems().size();
    for (int i = 0; i < resultSize; i++) {
//    for (Track track : responseEntity.getBody().getTracks().getItems()) {
      Track track = responseEntity.getBody().getTracks().getItems().get(i);
      Track trackEn = responseEntityEn.getBody().getTracks().getItems().get(i);
      String imageUrl = null;
      List<Image> images = track.getAlbum().getImages();
      if (images != null && !images.isEmpty()) {
        imageUrl = images.get(0).getUrl();
      }

      String musicReleaseDate = track.getAlbum().getRelease_date();

      SpotifySearchResultDto spotifySearchResultDto = SpotifySearchResultDto.builder()
          .musicTitle(track.getName())
          .musicTitleEn(trackEn.getName())
          .musicArtist(track.getArtists().get(0).getName()) // Use get() to access the first artist
          .musicArtistEn(trackEn.getArtists().get(0).getName())
          .musicAlbum(track.getAlbum().getName())
          .musicImage(imageUrl)
          .musicReleaseDate(musicReleaseDate)
          .spotifyMusicDuration(track.getDuration_ms())
          .build();

      results.add(spotifySearchResultDto);
    }

    return results;
  }
}