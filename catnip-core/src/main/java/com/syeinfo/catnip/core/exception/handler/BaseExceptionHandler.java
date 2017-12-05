package com.syeinfo.catnip.core.exception.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

public abstract class BaseExceptionHandler {

    @Autowired
    protected MessageSource messageSource;

    protected String buildErrMsg(int errNum, String errMsgKey, String defaultMsg) {

        String errMsg = messageSource.getMessage(errMsgKey, null, defaultMsg, LocaleContextHolder.getLocale());

        StringBuilder builder = new StringBuilder();

        builder.append(errMsg);
        builder.append(" [");
        builder.append(errNum);
        builder.append("]");

        return builder.toString();

    }

}
