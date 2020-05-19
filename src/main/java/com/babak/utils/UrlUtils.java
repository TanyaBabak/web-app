package com.babak.utils;

public class UrlUtils {

    public static boolean isAjaxUrl(String url) {
        return url.startsWith("/ajax/");
    }

    public static boolean isAjaxJsonUrl(String url) {
        return url.startsWith("/ajax/json/");
    }

    public static boolean isAjaxHtmlUrl(String url) {
        return url.startsWith("/ajax/html/");
    }

    public static boolean isBootstrapUrl(String url) {
        return url.startsWith("/bootstrap/");
    }

    public static boolean isComponentsUrl(String url) {
        return url.startsWith("/components/");
    }

    private UrlUtils() {
    }
}
