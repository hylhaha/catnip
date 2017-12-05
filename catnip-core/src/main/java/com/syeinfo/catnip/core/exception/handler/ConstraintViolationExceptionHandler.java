package com.syeinfo.catnip.core.exception.handler;

import com.syeinfo.catnip.api.bean.ResponseHeader;
import com.syeinfo.catnip.api.RestfulResponse;
import com.syeinfo.catnip.core.common.CatnipErrorEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class ConstraintViolationExceptionHandler extends BaseExceptionHandler implements ExceptionMapper<ConstraintViolationException> {

    private Logger logger = LoggerFactory.getLogger(ConstraintViolationExceptionHandler.class);

    @Override
    public Response toResponse(ConstraintViolationException exception) {

        RestfulResponse response = new RestfulResponse();
        response.getResponseHeader().setErrorCode(CatnipErrorEnum.ERR_INVALID_REQUEST_FORMAT.name());
        response.getResponseHeader().setStatus(ResponseHeader.STATUS_FAILED);

        String errMsg = buildErrMsg(CatnipErrorEnum.ERR_INVALID_REQUEST_FORMAT.getErrNum(),
                                    CatnipErrorEnum.ERR_INVALID_REQUEST_FORMAT.getErrMsgKey(),
                                    "ConstraintViolationException");
        response.getResponseHeader().setErrorMessage(errMsg);

        logger.error(exception.getMessage());

        return Response.ok(response).type("application/json").build();
    }
}
