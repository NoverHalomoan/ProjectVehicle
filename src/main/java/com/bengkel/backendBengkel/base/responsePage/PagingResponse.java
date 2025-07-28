package com.bengkel.backendBengkel.base.responsePage;

public class PagingResponse {

    private Integer currentPage;

    private Integer totalPage;

    private Integer size;

    public PagingResponse() {

    }

    public PagingResponse(Integer currentPage, Integer totalPage, Integer size) {
        this.currentPage = currentPage;
        this.totalPage = totalPage;
        this.size = size;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

}
