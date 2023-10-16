package com.rewe.distributor.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EmailValidatorTest {

    @Test
    public void email_validation_success_test() {
        boolean validationResult = EmailValidator.validate("test@mail.com");
        assertTrue(validationResult);
    }

    @Test
    public void email_validation_fail_test() {
        boolean validationResult = EmailValidator.validate("testmail.com");
        assertFalse(validationResult);
    }
}
