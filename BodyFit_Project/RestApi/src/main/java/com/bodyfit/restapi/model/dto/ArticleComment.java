package com.bodyfit.restapi.model.dto;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ArticleComment {
    private long commentId;
    private long userSeq;
    private long articleId;
    private String nickName;
    private String content;
    private Timestamp createTime;
    private Timestamp updateTime;
    private Byte deleted;
    private long groupId = 0L;
    private int groupOrder;
    private int depth;
    private long originalCommentId = 0L;
}
