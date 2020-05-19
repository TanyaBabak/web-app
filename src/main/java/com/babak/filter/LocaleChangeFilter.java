package com.babak.filter;

import com.babak.entity.CookieConfig;
import com.babak.utils.LocaleUtils;
import com.babak.utils.WebUtils;
import com.babak.utils.constants.JspConstants;
import com.babak.utils.wrapper.LocaleOverrideRequest;
import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter(filterName = "LocaleChangeFilter")
public class LocaleChangeFilter extends AbstractFilter {

    private List<Locale> initParametersValues;

    @Override
    public void init(FilterConfig filterConfig) {
        Enumeration<String> initParametersNames = filterConfig.getInitParameterNames();
        initParametersValues = receiveValuesInitParameters(initParametersNames, filterConfig);
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        Locale cookieLocale = findCookieLocale(request);
        Set<Locale> acceptHeaderLocale = findAcceptHeaderLocale(request);
        acceptHeaderLocale.forEach(System.out::println);
        Locale currentLocale = determineOverridingLocale(cookieLocale, acceptHeaderLocale, response, request);
        System.out.print("Filter: ");
        System.out.println(currentLocale);
        request.setAttribute(JspConstants.CURRENT_LOCALE, currentLocale);
        chain.doFilter(new LocaleOverrideRequest(request, currentLocale), response);
    }

    private List<Locale> receiveValuesInitParameters(Enumeration<String> initParametersNames, FilterConfig filterConfig) {
        List<String> streamEnumeration = Collections.list(initParametersNames);
        return streamEnumeration.stream().map(filterConfig::getInitParameter)
                .map(LocaleUtils::toLocale).collect(Collectors.toList());
    }

    private Locale findCookieLocale(HttpServletRequest request) {
        Cookie cookie = WebUtils.findCookie(request, CookieConfig.LOCALE_NAME.getName());
        if (Objects.nonNull(cookie)) {
            return LocaleUtils.toLocale(cookie.getValue());
        }
        return null;
    }

    private Set<Locale> findAcceptHeaderLocale(HttpServletRequest request) {
        List<Locale> enumerationList = Collections.list(request.getLocales());
        return new LinkedHashSet<>(enumerationList);
    }

    private Locale determineOverridingLocale(Locale cookieLocale, Set<Locale> acceptHeaderLocale, HttpServletResponse response, HttpServletRequest request) {
        if (Objects.isNull(cookieLocale)) {
            Locale locale = defineNeedfulLocale(acceptHeaderLocale);
            System.out.println("Cookie ");
            WebUtils.setCookie(CookieConfig.LOCALE_NAME.getName(), locale.toString(), CookieConfig.LOCALE_NAME.getTtl(), response);
            return locale;
        }
        System.out.println("Cookie2");
        System.out.println(WebUtils.findCookie(request, CookieConfig.LOCALE_NAME.getName()).getValue());
        return cookieLocale;
    }

    private Locale defineNeedfulLocale(Set<Locale> acceptHeaderLocale) {
        for (Locale locale : acceptHeaderLocale) {
            if (initParametersValues.contains(locale)) {
                return locale;
            }
        }
        return initParametersValues.get(2);
    }

}