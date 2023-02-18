package com.bodyfit.restapi.model.dto;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Article {
    private long articleId;
    private long userSeq;
    private String nickName;
    private int boardType;
    private long viewCount;
    private Timestamp createTime;
    private Timestamp updateTime;
    private String title;
    private String content;
}
