package com.babak.utils.strategy.validation.impl;

import com.babak.utils.constants.ErrorConstants;
import com.babak.utils.constants.UtilConstants;
import com.babak.utils.strategy.validation.Validation;
import org.apache.log4j.Logger;

public class ValidationEmail implements Validation {

    private static final Logger LOGGER = Logger.getLogger(ValidationEmail.class);

    @Override
    public String executeValidation(String email) {
        if (email.matches(UtilConstants.REGEX_EMAIL) && !email.isEmpty()) {
            LOGGER.debug(email + UtilConstants.RIGHT);
            return UtilConstants.RIGHT;
        }
        LOGGER.debug(email + ErrorConstants.WRONG);
        return ErrorConstants.ERROR_EMAIL;
    }
}
