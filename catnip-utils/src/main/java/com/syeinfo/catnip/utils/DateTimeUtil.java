package com.syeinfo.catnip.utils;

import org.joda.time.DateTime;

import java.util.Date;

public class DateTimeUtil {

    public static Date getNow() {

        String mockDate = System.getProperty("mock.date");

        if (null != mockDate && mockDate.length() > 0) {
            return new DateTime(mockDate).toDate();
        } else {
            return new Date();
        }

    }

}
