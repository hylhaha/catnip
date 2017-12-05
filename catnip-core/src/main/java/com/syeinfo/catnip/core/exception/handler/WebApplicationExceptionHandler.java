package com.syeinfo.catnip.core.exception.handler;

import com.syeinfo.catnip.api.bean.ResponseHeader;
import com.syeinfo.catnip.api.RestfulResponse;
import com.syeinfo.catnip.core.common.CatnipErrorEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class WebApplicationExceptionHandler extends BaseExceptionHandler implements ExceptionMapper<WebApplicationException> {

    private Logger logger = LoggerFactory.getLogger(WebApplicationExceptionHandler.class);

    @Override
    public Response toResponse(WebApplicationException exception) {

        RestfulResponse response = new RestfulResponse();
        response.getResponseHeader().setErrorCode(CatnipErrorEnum.ERR_INTERNAL_ERROR.name());
        response.getResponseHeader().setStatus(ResponseHeader.STATUS_FAILED);

        String errMsg = buildErrMsg(CatnipErrorEnum.ERR_INTERNAL_ERROR.getErrNum(),
                                    CatnipErrorEnum.ERR_INTERNAL_ERROR.getErrMsgKey(),
                                    "WebApplicationException");
        response.getResponseHeader().setErrorMessage(errMsg);

        logger.error(exception.getMessage(), exception);

        return Response.ok(response).type("application/json").build();

    }
}
