package com.bodyfit.restapi.controller;

import com.bodyfit.restapi.model.dto.*;
import com.bodyfit.restapi.model.service.ArticleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/articles")
@CrossOrigin(origins = {"*"}, methods = {RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.POST})
@ApiOperation("게시글과 관련된 컨트롤러입니다.")
public class ArticleController {
    private final ArticleService articleService;
    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping("")
    @ApiOperation("게시글을 등록합니다. userSeq, title, content가 필요합니다.")
    public ResponseEntity<Void> createArticle(@RequestBody Article article) {
        try {
            articleService.createArticle(article);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("")
    @ApiOperation("게시글을 수정합니다. articleId, title, content가 필요합니다.")
    public ResponseEntity<Void> modifyArticle(@RequestBody Article article) {
        try {
            articleService.updateArticle(article);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{articleId}")
    @ApiOperation("게시글을 삭제합니다. articleId가 필요합니다.")
    public ResponseEntity<Void> deleteArticle(@PathVariable long articleId) {
        try {
            articleService.deleteArticle(articleId);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{articleId}")
    @ApiOperation("Detail화면을 위해 게시글 하나를 가져옵니다. articleId가 필요합니다.")
    public ResponseEntity<?> getArticle(@PathVariable long articleId) {
        try {
            Optional<Article> article = articleService.getArticleDetail(articleId);
            if (article.isPresent()) {
                return new ResponseEntity<>(article.get(), HttpStatus.ACCEPTED);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/search")
    @ApiOperation("조건에 맞는 게시글들을 가져옵니다. mod는 title, content, nickName이 있습니다.")
    public ResponseEntity<?> searchVideos(SearchCondition searchCondition) {
        try {
            ListAndNavigation<Article> ArticleList = articleService.searchArticles(searchCondition);
            if (ArticleList.getList() != null && ArticleList.getList().size() > 0) {
                return new ResponseEntity<>(ArticleList, HttpStatus.ACCEPTED);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{articleId}/comments")
    @ApiOperation("게시글 댓글을 작성합니다. content, originalCommentId, articleId, userSeq가 필요합니다.")
    public ResponseEntity<Void> createArticleComment(@RequestBody ArticleComment articleComment) {
        try {
            articleService.createComment(articleComment);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{articleId}/comments")
    @ApiOperation("게시글 댓글을 수정합니다.")
    public ResponseEntity<Void> updateArticleComment(@RequestBody ArticleComment articleComment) {
        try {
            articleService.updateComment(articleComment);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{articleId}/comments/{commentId}")
    @ApiOperation("게시글 댓글을 삭제합니다. 사실 지우는 것이 아니라 삭제됨(deleted)로 덮어씌움")
    public ResponseEntity<Void> deleteComment(@PathVariable long commentId) {
        try {
            articleService.deleteComment(commentId);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{articleId}/comments/{pageNum}")
    @ApiOperation("게시글의 댓글들을 가져옵니다.")
    public ResponseEntity<?> getCommentsByArticleId(@PathVariable long articleId, @PathVariable int pageNum) {
        try {
            ListAndNavigation<ArticleComment> comments = articleService.commentsByArticleId(articleId, pageNum);
            if (comments.getList() != null && comments.getList().size() > 0) {
                return new ResponseEntity<>(comments, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{articleId}/comments")
    @ApiOperation("게시글의 댓글들을 가져옵니다.")
    public ResponseEntity<?> getRecentCommentsByArticleId(@PathVariable long articleId) {
        try {
            ListAndNavigation<ArticleComment> comments = articleService.commentsByArticleId(articleId);
            if (comments.getList() != null && comments.getList().size() > 0) {
                return new ResponseEntity<>(comments, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
