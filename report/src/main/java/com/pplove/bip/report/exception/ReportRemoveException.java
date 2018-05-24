package com.pplove.bip.report.exception;

import com.pplove.bip.exception.ApiException;
import com.pplove.bip.response.ErrorCode;
import com.pplove.bip.exception.ApiException;
import com.pplove.bip.response.ErrorCode;

/**
 * Created by jiatingjin on 2018/1/23.
 */
public class ReportRemoveException extends ApiException {
    public ReportRemoveException(String r, String msg) {
        super(ErrorCode.NO_PRIVILEGE, String.format("cannot remove %s because of depended %s", r, msg));
    }
}
