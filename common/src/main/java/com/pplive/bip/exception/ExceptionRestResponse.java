package com.pplive.bip.exception;

import com.pplive.bip.response.RestResponse;


public class ExceptionRestResponse extends RestResponse<ApiException> {
    public ExceptionRestResponse(ApiException data) {
        super(data.getErrorCode(), data.getMessage());
    }
}
