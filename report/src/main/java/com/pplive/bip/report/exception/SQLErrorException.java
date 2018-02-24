package com.pplive.bip.report.exception;

import com.pplive.bip.exception.ApiException;
import com.pplive.bip.response.ErrorCode;

/**
 * Created by jiatingjin on 2018/1/30.
 */
public class SQLErrorException extends ApiException {

    public SQLErrorException(String message){
        super(ErrorCode.SQL_ERROR, message);
    }
}
