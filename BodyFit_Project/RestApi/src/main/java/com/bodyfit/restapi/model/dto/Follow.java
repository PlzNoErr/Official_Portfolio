package com.bodyfit.restapi.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Follow {
    private long followedSeq;
    private long followingSeq;
}
