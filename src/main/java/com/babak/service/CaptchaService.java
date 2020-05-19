package com.babak.service;

import com.babak.entity.Captcha;

public interface CaptchaService {

    Captcha createCaptcha();

    Captcha receiveImageById(long id);

    boolean checkCaptchaByString(String answer);

    boolean checkTimeOutForRegistration(long id, long finishTime);

    void removeCaptcha(long id);

    void putCaptcha(Captcha captcha);
}
