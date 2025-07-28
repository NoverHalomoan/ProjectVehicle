package com.bengkel.backendBengkel.base.responsePage;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WebResponse<T> {

    private T data;

    private Boolean status;

    private PagingResponse paging;

    private int StatusCode;

    private String message;

    public WebResponse() {

    }

    public WebResponse(T data, Boolean status, PagingResponse paging, int StatusCode, String message) {
        this.data = data;
        this.status = status;
        this.paging = paging;
        this.StatusCode = StatusCode;
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Boolean getErrors() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public PagingResponse getPaging() {
        return paging;
    }

    public void setPaging(PagingResponse paging) {
        this.paging = paging;
    }

    public int getStatusCode() {
        return StatusCode;
    }

    public void setStatusCode(int statusCode) {
        StatusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
