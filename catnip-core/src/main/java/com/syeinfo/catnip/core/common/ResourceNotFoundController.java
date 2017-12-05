package com.syeinfo.catnip.core.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.MessageFormat;
import java.util.Date;

@Controller
@ControllerAdvice
public class ResourceNotFoundController {

    private String errKey = "err.common.exception";

    @Autowired
    private MessageSource messageSource;

    @RequestMapping(value = "{path:(?!api).*$}", produces = "application/json")
    @ResponseBody
    public String fallbackHandler() throws Exception {

        String errMsg = messageSource.getMessage(errKey, null, "NotFoundException", LocaleContextHolder.getLocale());

        String pattern = "'{'\"responseHeader\":'{'\"status\":0,\"errorCode\":\"ERR_RESOURCE_NOT_FOUND\",\"errorMessage\":\"{0} [404]\",\"timestamp\":{1}'}}'";
        return MessageFormat.format(pattern, errMsg, String.valueOf(new Date().getTime()));

    }

}