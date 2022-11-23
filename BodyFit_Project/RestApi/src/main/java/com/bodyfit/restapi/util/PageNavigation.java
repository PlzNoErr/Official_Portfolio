package com.bodyfit.restapi.util;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PageNavigation {

    private int startPage;
    private int endPage;
    private boolean startRange;
    private boolean endRange;
    private int totalCount;
    private int totalPageCount;
    private int currentPage;
    private int countPerPage;
    private int itemPerPage = 10;
    private int maxDisplayNavCnt = 10;

    public PageNavigation(int currentPage, int totalCount, int itemPerPage) {
        makePageNavigation(currentPage, totalCount, itemPerPage);
    }
    public PageNavigation(int totalCount, int itemPerPage) {
        makePageNavigation(totalCount, itemPerPage);
    }


    /**
     * pagination make
     * @param currentPage
     * @param totalCount
     * @param itemPerPage
     */
    private void makePageNavigation(int currentPage, int totalCount, int itemPerPage) {
        this.setItemPerPage(itemPerPage);
        this.setCurrentPage(currentPage);
        this.setTotalCount(totalCount);
        this.setCountPerPage(itemPerPage);

        int totalPageCount = (totalCount - 1) / itemPerPage + 1;
        this.setTotalPageCount(totalPageCount);

        this.setStartPage((currentPage - 1) / maxDisplayNavCnt * maxDisplayNavCnt + 1);

        this.setEndPage(this.getStartPage() + maxDisplayNavCnt - 1);
        if (totalPageCount < this.getEndPage()) {
            this.setEndPage(totalPageCount);
        }
        this.setStartRange(currentPage <= maxDisplayNavCnt);
        boolean endRange = (totalPageCount - 1) / maxDisplayNavCnt * maxDisplayNavCnt < currentPage;
        this.setEndRange(endRange);
    }
    private void makePageNavigation(int totalCount, int itemPerPage) {
        this.setItemPerPage(itemPerPage);
        this.setTotalCount(totalCount);
        this.setCountPerPage(itemPerPage);

        int totalPageCount = (totalCount - 1) / itemPerPage + 1;
        this.setTotalPageCount(totalPageCount);
        this.setCurrentPage(totalPageCount);

        this.setStartPage((totalPageCount - 1) / maxDisplayNavCnt * maxDisplayNavCnt + 1);

        this.setEndPage(this.getStartPage() + maxDisplayNavCnt - 1);
        if (totalPageCount < this.getEndPage()) {
            this.setEndPage(totalPageCount);
        }
        this.setStartRange(totalPageCount <= maxDisplayNavCnt);
        boolean endRange = (totalPageCount - 1) / maxDisplayNavCnt * maxDisplayNavCnt < totalPageCount;
        this.setEndRange(endRange);
    }

}
