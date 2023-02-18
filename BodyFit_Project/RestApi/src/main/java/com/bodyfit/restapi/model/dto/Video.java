package com.bodyfit.restapi.model.dto;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Video {
    private long videoId;
    private String videoUrl;
    private String channel;
    private String title;
    private String description;
    private String category;
    private int likeCount;
    private long viewCount;
    private long reviewCount;
    private Timestamp createTime;
    private Timestamp updateTime;

}
