package com.pplove.bip.response;

/**
 * 跨应用通信优先考虑Rest方式返回错误码
 */
public class RestResponse<T> {

    public static RestResponse NullRestResponse = new RestResponse<>(null);

    private int    errorCode;
    private String errorMessage;
    private T      data;

    //成功响应
    public RestResponse(T data) {
        this.errorCode = 0;
        this.errorMessage = "Success";
        this.data = data;
    }

    //失败响应
    public RestResponse(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


}
