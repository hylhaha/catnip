package com.syeinfo.catnip.api.validator;

import com.syeinfo.catnip.api.annotation.ServiceId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ServiceIdValidator implements ConstraintValidator<ServiceId, String> {

    private static final String KEY_REGEX_SERVICE_ID = "catnip.regex.service.id";

    private String serviceIdRegex;

    @Autowired
    private Environment ev;

    @Override
    public void initialize(ServiceId constraintAnnotation) {
        this.serviceIdRegex = ev.getProperty(KEY_REGEX_SERVICE_ID);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        Pattern p = Pattern.compile(serviceIdRegex);
        Matcher m = p.matcher(value);

        return m.find();

    }

}
