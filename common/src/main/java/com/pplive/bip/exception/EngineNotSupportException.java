package com.pplive.bip.exception;

import com.pplive.bip.response.ErrorCode;

/**
 * Created by jiatingjin on 2018/1/2.
 */
public class EngineNotSupportException extends ApiException {

    public EngineNotSupportException(String e) {
        super(ErrorCode.ENGINE_NOT_SUPPORT, String.format("engine[%s] does not support", e));
    }
}
