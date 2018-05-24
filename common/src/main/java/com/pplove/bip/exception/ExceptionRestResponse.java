package com.pplove.bip.exception;

import com.pplove.bip.response.RestResponse;
import com.pplove.bip.response.RestResponse;


public class ExceptionRestResponse extends RestResponse<ApiException> {
    public ExceptionRestResponse(ApiException data) {
        super(data.getErrorCode(), data.getMessage());
    }
}
