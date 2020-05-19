package com.babak.utils.strategy.storage;

import com.babak.service.CaptchaService;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface StorageData {

    void setAttributes(HttpServletRequest request, HttpServletResponse response, long captchaId, CaptchaService captchaService) throws IOException;

    Long getAttributeId(HttpServletRequest request);

    CaptchaService getAttributeCaptchaService(HttpServletRequest request);
}
