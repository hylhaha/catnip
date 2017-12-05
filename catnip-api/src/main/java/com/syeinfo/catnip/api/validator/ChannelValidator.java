package com.syeinfo.catnip.api.validator;

import com.syeinfo.catnip.api.annotation.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChannelValidator implements ConstraintValidator<Channel, String> {

    private static final String KEY_REGEX_CHANNEL = "catnip.regex.channel";

    private String channelRegex;

    @Autowired
    private Environment ev;

    @Override
    public void initialize(Channel constraintAnnotation) {
        this.channelRegex = ev.getProperty(KEY_REGEX_CHANNEL);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Pattern p = Pattern.compile(channelRegex);
        Matcher m = p.matcher(value);

        return m.find();
    }
}
