package com.babak.utils.strategy.validation.impl;

import com.babak.utils.constants.ErrorConstants;
import com.babak.utils.constants.UtilConstants;
import com.babak.utils.strategy.validation.Validation;
import org.apache.log4j.Logger;

public class ValidationPassword implements Validation {

    private static final Logger LOGGER = Logger.getLogger(ValidationPassword.class);

    @Override
    public String executeValidation(String password) {
        if (password.length() > UtilConstants.NUMBER_FOR_RESTRICTION) {
            LOGGER.debug(password + UtilConstants.RIGHT);
            return UtilConstants.RIGHT;
        }
        LOGGER.debug(password + ErrorConstants.WRONG);
        return ErrorConstants.ERROR_PASSWORD;
    }
}

