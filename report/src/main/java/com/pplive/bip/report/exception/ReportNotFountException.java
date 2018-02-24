package com.pplive.bip.report.exception;

import com.pplive.bip.exception.ApiException;
import com.pplive.bip.response.ErrorCode;


public class ReportNotFountException extends ApiException {
    public ReportNotFountException(String reportName) {
        super(ErrorCode.REPORT_NOT_FOUNT, String.format("Report with name[%s] does not exist", reportName));
    }

    public ReportNotFountException(Long reportId) {
        super(ErrorCode.REPORT_NOT_FOUNT, String.format("Report with Id[%s] does not exist", reportId));
    }
}
