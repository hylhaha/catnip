package com.syeinfo.catnip.core.exception.handler;

import com.syeinfo.catnip.api.bean.ResponseHeader;
import com.syeinfo.catnip.api.RestfulResponse;
import com.syeinfo.catnip.core.common.CatnipErrorEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import javax.ws.rs.NotFoundException;

public class NotFoundExceptionHandler extends BaseExceptionHandler implements ExceptionMapper<NotFoundException> {

    private Logger logger = LoggerFactory.getLogger(NotFoundExceptionHandler.class);

    @Override
    public Response toResponse(NotFoundException exception) {

        RestfulResponse response = new RestfulResponse();
        response.getResponseHeader().setErrorCode(CatnipErrorEnum.ERR_RESOURCE_NOT_FOUND.name());
        response.getResponseHeader().setStatus(ResponseHeader.STATUS_FAILED);

        String errMsg = buildErrMsg(CatnipErrorEnum.ERR_RESOURCE_NOT_FOUND.getErrNum(),
                                    CatnipErrorEnum.ERR_RESOURCE_NOT_FOUND.getErrMsgKey(),
                                    "NotFoundException");
        response.getResponseHeader().setErrorMessage(errMsg);

        logger.error(exception.getMessage(), exception);

        return Response.ok(response).type("application/json").build();
    }
}
