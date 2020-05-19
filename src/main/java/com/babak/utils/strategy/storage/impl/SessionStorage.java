package com.babak.utils.strategy.storage.impl;

import com.babak.service.CaptchaService;
import com.babak.utils.constants.WebConstants;
import com.babak.utils.strategy.storage.StorageData;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

public class SessionStorage implements StorageData {

    private static final  Logger LOGGER = Logger.getLogger(SessionStorage.class);

    @Override
    public void setAttributes(HttpServletRequest request, HttpServletResponse response, long captchaId, CaptchaService captchaService) {
        HttpSession session = request.getSession();
        session.setAttribute(WebConstants.CAPTCHA_ID, captchaId);
        session.setAttribute(WebConstants.SERVICE_CAPTCHA, captchaService);
        LOGGER.debug("Attributes were set in session");
    }

    @Override
    public Long getAttributeId(HttpServletRequest request) {
        HttpSession session = request.getSession();
        LOGGER.debug("Id was get with session");
        return (Long) session.getAttribute(WebConstants.CAPTCHA_ID);
    }

    @Override
    public CaptchaService getAttributeCaptchaService(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (CaptchaService) session.getAttribute(WebConstants.SERVICE_CAPTCHA);
    }
}
