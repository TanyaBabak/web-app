package com.babak.utils.strategy.storage.impl;

import com.babak.service.CaptchaService;
import com.babak.utils.constants.WebConstants;
import com.babak.utils.strategy.storage.StorageData;
import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class HiddenFieldStorage implements StorageData {

    private static final Logger LOGGER = Logger.getLogger(HiddenFieldStorage.class);

    @Override
    public void setAttributes(HttpServletRequest request, HttpServletResponse response, long captchaId, CaptchaService captchaService) throws IOException {
        ServletContext servletContext = request.getServletContext();
        servletContext.setAttribute(WebConstants.CAPTCHA_ID, captchaId);
        servletContext.setAttribute(WebConstants.SERVICE_CAPTCHA, captchaService);
        servletContext.setAttribute(WebConstants.HIDDEN_FIELD, true);
        LOGGER.debug("Attribute set in context");
    }

    @Override
    public Long getAttributeId(HttpServletRequest request) {
        LOGGER.debug("Get id captcha with hidden field");
        return (Long) request.getServletContext().getAttribute(WebConstants.CAPTCHA_ID);
    }

    @Override
    public CaptchaService getAttributeCaptchaService(HttpServletRequest request) {
        return (CaptchaService) request.getServletContext().getAttribute(WebConstants.SERVICE_CAPTCHA);
    }
}
