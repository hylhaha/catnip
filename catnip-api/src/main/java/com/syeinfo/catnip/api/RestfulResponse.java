package com.syeinfo.catnip.api;

import com.syeinfo.catnip.api.base.BaseResponse;
import com.syeinfo.catnip.api.bean.ResponseHeader;
import com.syeinfo.catnip.utils.DateTimeUtil;

public class RestfulResponse<T extends BaseResponse> {

    private ResponseHeader responseHeader;

    private T payload;

    public RestfulResponse() {
        responseHeader = new ResponseHeader();
        responseHeader.setTimestamp(DateTimeUtil.getNow());
    }

    public ResponseHeader getResponseHeader() {
        return responseHeader;
    }

    public void setResponseHeader(ResponseHeader responseHeader) {
        this.responseHeader = responseHeader;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    public void setError(String errorCode, String errorMessage) {
        responseHeader.setErrorCode(errorCode);
        responseHeader.setErrorMessage(errorMessage);
    }

    public void setFailedStatus() {
        responseHeader.setStatus(ResponseHeader.STATUS_FAILED);
    }
}
