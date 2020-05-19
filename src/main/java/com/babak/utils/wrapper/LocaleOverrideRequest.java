package com.babak.utils.wrapper;

import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class LocaleOverrideRequest extends HttpServletRequestWrapper {

    private Locale locale;

    public LocaleOverrideRequest(HttpServletRequest httpServletRequest, Locale locale) {
        super(httpServletRequest);
        this.locale = locale;
    }

    @Override
    public Locale getLocale() {
        return Objects.nonNull(locale) ? locale : super.getLocale();
    }

    @Override
    public Enumeration<Locale> getLocales() {
        if (Objects.isNull(locale)) {
            return super.getLocales();
        } else {
            List<Locale> locales = Collections.list(super.getLocales());
            if (locales.contains(locale)) {
                locales.remove(locale);
            }
            locales.add(0, locale);
            return Collections.enumeration(locales);
        }
    }
}
