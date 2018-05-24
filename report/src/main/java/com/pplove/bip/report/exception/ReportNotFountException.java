package com.pplove.bip.report.exception;

import com.pplove.bip.exception.ApiException;
import com.pplove.bip.response.ErrorCode;
import com.pplove.bip.exception.ApiException;
import com.pplove.bip.response.ErrorCode;


public class ReportNotFountException extends ApiException {
    public ReportNotFountException(String reportName) {
        super(ErrorCode.REPORT_NOT_FOUNT, String.format("Report with name[%s] does not exist", reportName));
    }

    public ReportNotFountException(Long reportId) {
        super(ErrorCode.REPORT_NOT_FOUNT, String.format("Report with Id[%s] does not exist", reportId));
    }
}
