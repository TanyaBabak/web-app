package com.babak.utils.strategy.validation.impl;

import com.babak.utils.constants.ErrorConstants;
import com.babak.utils.constants.UtilConstants;
import com.babak.utils.strategy.validation.Validation;
import java.util.Objects;
import org.apache.log4j.Logger;

public class ValidationPasswordConfirmation implements Validation {

    private static final Logger LOGGER = Logger.getLogger(ValidationPasswordConfirmation.class);
    private String password;

    public ValidationPasswordConfirmation(String password) {
        this.password = password;
    }

    @Override
    public String executeValidation(String passwordConfirmation) {
        if (!password.isEmpty() && Objects.equals(passwordConfirmation, password)) {
            LOGGER.debug(passwordConfirmation + " is coincide");
            return UtilConstants.RIGHT;
        }
        LOGGER.debug(passwordConfirmation + " isn't coincide");
        return ErrorConstants.ERROR_PASSWORD_CONFIRMATION;
    }
}
