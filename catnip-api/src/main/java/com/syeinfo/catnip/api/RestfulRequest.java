package com.syeinfo.catnip.api;

import com.syeinfo.catnip.api.base.BaseRequest;
import com.syeinfo.catnip.api.bean.RequestHeader;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class RestfulRequest<T extends BaseRequest> {

    @Valid
    @NotNull
    private RequestHeader requestHeader;

    @Valid
    @NotNull
    private T payload;

    public RequestHeader getRequestHeader() {
        return requestHeader;
    }

    public void setRequestHeader(RequestHeader requestHeader) {
        this.requestHeader = requestHeader;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }
}
