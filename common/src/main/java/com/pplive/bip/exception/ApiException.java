package com.pplive.bip.exception;

public class ApiException extends RuntimeException{
    private int errorCode;

    public ApiException(int errorCode, String message){
        super(message);
        this.errorCode = errorCode;
    }

    public ApiException(int errorCode, String message, Throwable ex){
        super(message, ex);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
