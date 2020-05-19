package com.babak.entity;

public enum CookieConfig {

    SHOPPING_CART("product", 60 * 60 * 24 * 365),
    CAPTCHA_ID("captchaId", 60 * 30),
    LOCALE_NAME("local", 60 * 30);

    private final String name;
    private final int ttl;

    private CookieConfig(String name, int ttl) {
        this.name = name;
        this.ttl = ttl;
    }

    public String getName() {
        return name;
    }

    public int getTtl() {
        return ttl;
    }
}
