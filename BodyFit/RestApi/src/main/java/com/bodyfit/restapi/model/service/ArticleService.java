package com.bodyfit.restapi.model.service;

import com.bodyfit.restapi.model.dto.*;

import java.util.Optional;

public interface ArticleService {
    // 게시글 등록
    void createArticle(Article article);
    // 게시글 수정
    void updateArticle(Article article);
    // 게시글 삭제
    void deleteArticle(long articleId);
    // 게시글 Detail로 가기 위해 하나의 게시글 가져오기
    Optional<Article> getArticleDetail(long articleId);

    // 선택된 검색조건에 맞는 모든 게시글 목록 가져오기
    ListAndNavigation<Article> searchArticles(SearchCondition searchCondition);


    // 게시글에 코멘트 작성
    void createComment(ArticleComment articleComment);
    // 게시글에 코멘트 수정하기

    void updateComment(ArticleComment articleComment);
    // 게시글에 코멘트 삭제하기

    void deleteComment(long commentId);
    // 게시글별 코멘트 가져오기

    ListAndNavigation<ArticleComment> commentsByArticleId(long articleId, int pageNum);

    ListAndNavigation<ArticleComment> commentsByArticleId(long articleId);
}
