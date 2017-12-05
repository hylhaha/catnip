package com.syeinfo.catnip.core.common;

public enum CatnipErrorEnum {

    ERR_ACCESS_FORBIDDEN(403, "err.access.forbidden"),
    ERR_RESOURCE_NOT_FOUND(404, "err.resource.not.found"),
    ERR_INTERNAL_ERROR(500, "err.internal.error"),

    ERR_COMMON_EXCEPTION(2000, "err.common.exception"),
    ERR_JSON_MINIFY(2001, "err.json.minify"),
    ERR_MISSING_REMOTE_API(2002, "err.missing.remote.api"),

    ERR_INVALID_JSON_FORMAT(3000, "err.invalid.json.format"),
    ERR_INVALID_REQUEST_FORMAT(3100, "err.invalid.request.format"),

    ERR_INVALID_LOGIN_CERT(5000, "err.invalid.login.cret"),
    ERR_MISSING_TOKEN(5001, "err.missing.token"),
    ERR_ILLEGAL_TOKEN(5002, "err.illegal.token"),
    ERR_INVALID_TOKEN(5003, "err.invalid.token");

    private int errNbr;
    private String errMsgKey;

    CatnipErrorEnum(int errNbr, String errMsgKey) {
        this.errNbr = errNbr;
        this.errMsgKey = errMsgKey;
    }

    public String getErrMsgKey() {
        return this.errMsgKey;
    }

    public int getErrNum() {
        return this.errNbr;
    }

}
