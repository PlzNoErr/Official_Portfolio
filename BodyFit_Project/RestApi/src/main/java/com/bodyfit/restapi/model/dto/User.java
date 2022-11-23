package com.bodyfit.restapi.model.dto;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class User {
    private long userSeq;
    private String userId;
    private String password;
    private String email;
    private String nickName;
    private int rank;
    private Timestamp createTime;
    private int followedCount;
    private int followingCount;
}
