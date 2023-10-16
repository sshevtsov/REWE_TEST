package com.rewe.distributor.util;

import java.util.regex.Pattern;

/*
 * Class for validation e-mail address
 * */
public class EmailValidator {

    private static final String REGEX_EMAIL_PATTERN = "^(.+)@(\\S+)$";

    public static boolean validate(String emailAddress) {
        return Pattern.compile(REGEX_EMAIL_PATTERN)
                .matcher(emailAddress)
                .matches();
    }

}
