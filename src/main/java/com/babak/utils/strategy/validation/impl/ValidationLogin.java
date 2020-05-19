package com.babak.utils.strategy.validation.impl;

import com.babak.utils.constants.ErrorConstants;
import com.babak.utils.constants.UtilConstants;
import com.babak.utils.strategy.validation.Validation;
import org.apache.log4j.Logger;

public class ValidationLogin implements Validation {

    private static final Logger LOGGER = Logger.getLogger(ValidationLogin.class);

    @Override
    public String executeValidation(String login) {
        if (login.length() > UtilConstants.NUMBER_FOR_RESTRICTION) {
            LOGGER.debug(login + UtilConstants.RIGHT);
            return UtilConstants.RIGHT;
        }
        LOGGER.debug(login + ErrorConstants.WRONG);
        return ErrorConstants.ERROR_LOGIN;
    }
}
