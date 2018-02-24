package com.pplive.bip.report.exception;

import com.pplive.bip.exception.ApiException;
import com.pplive.bip.response.ErrorCode;

/**
 * Created by jiatingjin on 2018/1/22.
 */
public class AuditException extends ApiException {
    public AuditException(String message) {
        super(ErrorCode.SCHEMA_INVALID, message);
    }
}
