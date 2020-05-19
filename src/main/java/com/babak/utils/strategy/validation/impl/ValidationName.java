package com.babak.utils.strategy.validation.impl;

import com.babak.utils.constants.ErrorConstants;
import com.babak.utils.constants.UtilConstants;
import com.babak.utils.strategy.validation.Validation;
import org.apache.log4j.Logger;

public class ValidationName implements Validation {

    private static final Logger LOGGER = Logger.getLogger(ValidationName.class);

    @Override
    public String executeValidation(String name) {
        if (name.matches(UtilConstants.REGEX_NAME)) {
            LOGGER.debug(name + UtilConstants.RIGHT);
            return UtilConstants.RIGHT;
        }
        LOGGER.debug(name + ErrorConstants.WRONG);
        return ErrorConstants.ERROR_NAME;
    }
}
