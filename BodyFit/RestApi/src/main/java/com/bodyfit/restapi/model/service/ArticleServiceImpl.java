package com.bodyfit.restapi.model.service;

import com.bodyfit.restapi.model.dao.ArticleDao;
import com.bodyfit.restapi.model.dto.*;
import com.bodyfit.restapi.util.PageNavigation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleServiceImpl implements ArticleService{

    public final int ARTICLE_PER_PAGE = 15;
    public final int COMMENTS_PER_PAGE = 20;
    private ArticleDao articleDao;
    // 생성자
    @Autowired
    public ArticleServiceImpl(ArticleDao articleDao) {
        this.articleDao = articleDao;
    }
    
    // <-- 메소드 작성 --> //
    @Override
    public void createArticle(Article article) {
        articleDao.insertArticle(article);
    }

    @Override
    public void updateArticle(Article article) {
        articleDao.updateArticle(article);
    }

    @Override
    public void deleteArticle(long articleId) {
        articleDao.deleteArticle(articleId);
    }

    @Override
    public Optional<Article> getArticleDetail(long articleId) {
        articleDao.updateViewCountByArticleId(articleId);
        return articleDao.selectArticleById(articleId);
    }

    @Override
    public ListAndNavigation<Article> searchArticles(SearchCondition searchCondition) {
        searchCondition.setCountPerPage(ARTICLE_PER_PAGE);
        PageNavigation pageNavigation = new PageNavigation(searchCondition.getCurrentPage(), articleDao.getTotalByCondition(searchCondition) , ARTICLE_PER_PAGE);
        List<Article> list = articleDao.selectAllArticlesByCondition(searchCondition);
        return new ListAndNavigation<>(list, pageNavigation);
    }

    @Override
    public void createComment(ArticleComment articleComment) {
        if (articleComment.getOriginalCommentId() == 0L) {
            articleDao.insertComment(articleComment);
            articleComment.setGroupId(articleComment.getCommentId());
            articleDao.updateComment(articleComment);
        } else {
            Optional<ArticleComment> originalComment = articleDao.selectArticleCommentById(articleComment.getOriginalCommentId());
            if (originalComment.isPresent()) {
                articleComment.setGroupId(originalComment.get().getGroupId());
                articleComment.setGroupOrder(articleDao.getCommentOrder((originalComment.get())));
                articleComment.setDepth(originalComment.get().getDepth() + 1);
                articleDao.updateCommentsOrder(articleComment);
                articleDao.insertComment(articleComment);
            }
        }
    }

    @Override
    public void updateComment(ArticleComment articleComment) {
        articleDao.updateComment(articleComment);
    }

    @Override
    public void deleteComment(long commentId) {
        articleDao.deleteComment(commentId);
    }

    @Override
    public ListAndNavigation<ArticleComment> commentsByArticleId(long articleId, int pageNum) {
        PageNavigation pageNavigation = new PageNavigation(pageNum, articleDao.getTotalCommentByArticleId(articleId), COMMENTS_PER_PAGE);
        PageCondition pageCondition = PageCondition.builder().articleId(articleId).currentPage(pageNum).countPerPage(COMMENTS_PER_PAGE).build();
        List<ArticleComment> list = articleDao.selectCommentsByArticleId(pageCondition);
        return new ListAndNavigation<>(list, pageNavigation);
    }

    @Override
    public ListAndNavigation<ArticleComment> commentsByArticleId(long articleId) {
        PageNavigation pageNavigation = new PageNavigation(articleDao.getTotalCommentByArticleId(articleId), COMMENTS_PER_PAGE);
        PageCondition pageCondition = PageCondition.builder().articleId(articleId).currentPage(pageNavigation.getCurrentPage()).countPerPage(COMMENTS_PER_PAGE).build();
        List<ArticleComment> list = articleDao.selectCommentsByArticleId(pageCondition);
        return new ListAndNavigation<>(list, pageNavigation);
    }

    // 코멘트 해당 부분
}
