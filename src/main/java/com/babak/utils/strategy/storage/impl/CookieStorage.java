package com.babak.utils.strategy.storage.impl;

import com.babak.entity.CookieConfig;
import com.babak.service.CaptchaService;
import com.babak.utils.WebUtils;
import com.babak.utils.constants.WebConstants;
import com.babak.utils.strategy.storage.StorageData;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class CookieStorage implements StorageData {

    private static final Logger LOGGER = Logger.getLogger(CookieStorage.class);

    @Override
    public void setAttributes(HttpServletRequest request, HttpServletResponse response, long captchaId, CaptchaService captchaService) {
        ServletContext servletContext = request.getServletContext();
        servletContext.setAttribute(WebConstants.SERVICE_CAPTCHA, captchaService);
        WebUtils.setCookie(CookieConfig.CAPTCHA_ID.getName(), String.valueOf(captchaId), CookieConfig.CAPTCHA_ID.getTtl(), response);
        LOGGER.debug("Attribute put in cookie");
    }

    @Override
    public Long getAttributeId(HttpServletRequest request) {
        Cookie cookie;
        if ((cookie = WebUtils.findCookie(request, WebConstants.CAPTCHA_ID)) != null) {
            LOGGER.debug("Id captcha was take with cookie");
            return Long.parseLong(cookie.getValue());
        }
        LOGGER.debug("Id captcha wasn't take with cookie");
        return null;
    }

    @Override
    public CaptchaService getAttributeCaptchaService(HttpServletRequest request) {
        return (CaptchaService) request.getServletContext().getAttribute(WebConstants.SERVICE_CAPTCHA);
    }
}
