package com.syeinfo.catnip.core.exception.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.syeinfo.catnip.api.bean.ResponseHeader;
import com.syeinfo.catnip.api.RestfulResponse;
import com.syeinfo.catnip.core.common.CatnipErrorEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class JsonProcessingExceptionHandler extends BaseExceptionHandler implements ExceptionMapper<JsonProcessingException> {

    private Logger logger = LoggerFactory.getLogger(JsonProcessingExceptionHandler.class);

    @Override
    public Response toResponse(JsonProcessingException exception) {

        RestfulResponse response = new RestfulResponse();
        response.getResponseHeader().setErrorCode(CatnipErrorEnum.ERR_INVALID_JSON_FORMAT.name());
        response.getResponseHeader().setStatus(ResponseHeader.STATUS_FAILED);

        String errMsg = buildErrMsg(CatnipErrorEnum.ERR_INVALID_JSON_FORMAT.getErrNum(),
                                    CatnipErrorEnum.ERR_INVALID_JSON_FORMAT.getErrMsgKey(),
                                    "JsonProcessingException");
        response.getResponseHeader().setErrorMessage(errMsg);

        logger.error(exception.getMessage(), exception);

        return Response.ok(response).type("application/json").build();
    }
}
