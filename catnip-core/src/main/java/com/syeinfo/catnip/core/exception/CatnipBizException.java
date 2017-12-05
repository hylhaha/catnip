package com.syeinfo.catnip.core.exception;

import com.syeinfo.catnip.core.common.CatnipErrorEnum;

public class CatnipBizException extends RuntimeException {

    private int errNum;

    private String errCode;
    private String errMsgKey;

    private String message;

    public CatnipBizException() {
        this.errNum = CatnipErrorEnum.ERR_COMMON_EXCEPTION.getErrNum();
        this.errCode = CatnipErrorEnum.ERR_COMMON_EXCEPTION.name();
        this.errMsgKey = CatnipErrorEnum.ERR_COMMON_EXCEPTION.getErrMsgKey();
    }

    public CatnipBizException(int errNum, String errCode, String errMsgKey) {
        this.errNum = errNum;
        this.errCode = errCode;
        this.errMsgKey = errMsgKey;
    }

    public CatnipBizException(String errCode, String message) {
        this.errCode = errCode;
        this.message = message;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMsgKey() {
        return errMsgKey;
    }

    public void setErrMsgKey(String errMsgKey) {
        this.errMsgKey = errMsgKey;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getErrNum() {
        return errNum;
    }

    public void setErrNum(int errNum) {
        this.errNum = errNum;
    }
}
