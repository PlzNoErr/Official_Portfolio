package com.bodyfit.restapi.model.dao;

import com.bodyfit.restapi.model.dto.*;

import java.util.List;
import java.util.Optional;

public interface ArticleDao {

    // 게시글 생성
    void insertArticle(Article article);
    // 게시글 수정
    void updateArticle(Article article);
    // 게시글 삭제
    void deleteArticle(long articleId);
    // 게시글 하나 가져오기 (articleId 활용)
    Optional<Article> selectArticleById(long articleId);
    // 조회수 증가
    void updateViewCountByArticleId(long articleId);
    
    //  게시글 목록 가져오기
    List<Article> selectAllArticlesByCondition(SearchCondition searchCondition);

    // 게시글 조건에 해당하는 글의 총 개수 가져오기
    int getTotalByCondition(SearchCondition searchCondition);

    
    
    // 게시글에 코멘트 작성
    int insertComment(ArticleComment articleComment);
    // 게시글에 코멘트 수정
    void updateComment(ArticleComment articleComment);
    // 게시글에 코멘트 Order 수정
    int updateCommentsOrder(ArticleComment articleComment);
    
    // 코멘트를 삭제한다
    void deleteComment(long commentId);
    
    // 코멘트 Order 최대값
    int getCommentOrder(ArticleComment articleComment);

    Optional<ArticleComment> selectArticleCommentById(long commentId);

    List<ArticleComment> selectCommentsByArticleId(PageCondition pageCondition);

    int getTotalCommentByArticleId(long articleId);


}
