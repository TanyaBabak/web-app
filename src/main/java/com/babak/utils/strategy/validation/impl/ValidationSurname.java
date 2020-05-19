package com.babak.utils.strategy.validation.impl;

import com.babak.utils.constants.ErrorConstants;
import com.babak.utils.constants.UtilConstants;
import com.babak.utils.strategy.validation.Validation;
import org.apache.log4j.Logger;

public class ValidationSurname implements Validation {

    private static final Logger LOGGER = Logger.getLogger(ValidationSurname.class);

    @Override
    public String executeValidation(String surname) {
        if (surname.matches(UtilConstants.REGEX_NAME)) {
            LOGGER.debug(surname + UtilConstants.RIGHT);
            return UtilConstants.RIGHT;
        }
        LOGGER.debug(surname + ErrorConstants.WRONG);
        return ErrorConstants.ERROR_SURNAME;
    }
}
