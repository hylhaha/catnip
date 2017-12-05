package com.syeinfo.catnip.core.filter;

import com.syeinfo.catnip.api.base.BaseRequest;

import javax.validation.constraints.NotNull;

public class AuthRequest extends BaseRequest {

    @NotNull
    private String params;

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

}
