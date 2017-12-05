package com.syeinfo.catnip.core.exception.handler;

import com.syeinfo.catnip.api.bean.ResponseHeader;
import com.syeinfo.catnip.api.RestfulResponse;
import com.syeinfo.catnip.core.common.CatnipErrorEnum;
import com.syeinfo.catnip.core.exception.CatnipBizException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class CommonExceptionHandler extends BaseExceptionHandler implements ExceptionMapper<Exception> {

    private Logger logger = LoggerFactory.getLogger(CommonExceptionHandler.class);

    @Override
    public Response toResponse(Exception exception) {

        RestfulResponse response = new RestfulResponse();
        response.getResponseHeader().setStatus(ResponseHeader.STATUS_FAILED);

        if (exception instanceof CatnipBizException) {

            setCatnipBizExceptionResponse((CatnipBizException) exception, response);

        } else {

            String errMsg = buildErrMsg(CatnipErrorEnum.ERR_COMMON_EXCEPTION.getErrNum(),
                                        CatnipErrorEnum.ERR_COMMON_EXCEPTION.getErrMsgKey(),
                                        "Common Exception");

            response.getResponseHeader().setErrorCode(CatnipErrorEnum.ERR_COMMON_EXCEPTION.name());
            response.getResponseHeader().setErrorMessage(errMsg);

            logger.error(exception.getMessage(), exception);

        }

        return Response.ok(response).type("application/json").build();
    }

    private void setCatnipBizExceptionResponse(CatnipBizException exception, RestfulResponse response) {

        String errMsg;

        if (null == exception.getErrMsgKey()) {
            errMsg = null == exception.getMessage() ? "CatnipBizException" : exception.getMessage();
        } else {
            errMsg = buildErrMsg(exception.getErrNum(), exception.getErrMsgKey(), "CatnipBizException");
        }

        response.getResponseHeader().setErrorCode(exception.getErrCode());
        response.getResponseHeader().setErrorMessage(errMsg);

    }

}
