package com.pplove.bip.report.exception;

import com.pplove.bip.exception.ApiException;
import com.pplove.bip.response.ErrorCode;
import com.pplove.bip.exception.ApiException;
import com.pplove.bip.response.ErrorCode;

/**
 * Created by jiatingjin on 2018/1/22.
 */
public class AuditException extends ApiException {
    public AuditException(String message) {
        super(ErrorCode.SCHEMA_INVALID, message);
    }
}
