package com.bodyfit.restapi.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class PageCondition {
    private int countPerPage = 10;
    private int currentPage = 1;

    private long userSeq;
    private long videoId;
    private long reviewId;
    private long articleId;

    /**
     * limit의 offset을 구한다.
     * 페이징으로 일부분만을 가져올 때 페이지의 시작 정보 위치로 메서드에 의해 계산된다.
     * 전달된 currentPage는 1부터 시작하고 DB는 0부터이므로 1을 빼준다.
     *
     * @return
     */
    public int getOffset() {
        return (this.currentPage - 1) * countPerPage;
    }
}
