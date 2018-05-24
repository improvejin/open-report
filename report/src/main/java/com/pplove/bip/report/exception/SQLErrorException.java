package com.pplove.bip.report.exception;

import com.pplove.bip.exception.ApiException;
import com.pplove.bip.response.ErrorCode;
import com.pplove.bip.exception.ApiException;
import com.pplove.bip.response.ErrorCode;

/**
 * Created by jiatingjin on 2018/1/30.
 */
public class SQLErrorException extends ApiException {

    public SQLErrorException(String message){
        super(ErrorCode.SQL_ERROR, message);
    }
}
