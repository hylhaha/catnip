package com.syeinfo.catnip.api.bean;

import com.syeinfo.catnip.utils.DateTimeUtil;

import java.util.Date;

public class ResponseHeader {

    public static final int STATUS_SUCCESS = 1;
    public static final int STATUS_FAILED = 0;

    private int status = STATUS_SUCCESS;

    private String errorCode;
    private String errorMessage;

    private Date timestamp = DateTimeUtil.getNow();

    public Date getTimestamp() {
        return null == timestamp ? null : new Date(timestamp.getTime());
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = null == timestamp ? null : new Date(timestamp.getTime());
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
