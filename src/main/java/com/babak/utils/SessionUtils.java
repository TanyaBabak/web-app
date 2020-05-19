package com.babak.utils;

import com.babak.entity.CookieConfig;
import com.babak.entity.User;
import com.babak.model.ShoppingCart;
import com.babak.utils.constants.WebConstants;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SessionUtils {

    public static ShoppingCart getCurrentShoppingCart(HttpServletRequest req) {
        return (ShoppingCart) req.getSession().getAttribute(WebConstants.CURRENT_SHOPPING_CART);
    }

    public static boolean isCurrentShoppingCartCreated(HttpServletRequest req) {
        return req.getSession().getAttribute(WebConstants.CURRENT_SHOPPING_CART) != null;
    }

    public static void setCurrentShoppingCart(HttpServletRequest req, ShoppingCart shoppingCart) {
        req.getSession().setAttribute(WebConstants.CURRENT_SHOPPING_CART, shoppingCart);
    }

    public static void clearCurrentShoppingCart(HttpServletRequest req, HttpServletResponse resp) {
        req.getSession().removeAttribute(WebConstants.CURRENT_SHOPPING_CART);
        WebUtils.setCookie(CookieConfig.SHOPPING_CART.getName(), null, 0, resp);
    }

    public static Cookie findShoppingCartCookie(HttpServletRequest req) {
        return WebUtils.findCookie(req, CookieConfig.SHOPPING_CART.getName());
    }

    public static void updateCurrentShoppingCartCookie(String cookieValue, HttpServletResponse resp) {
        WebUtils.setCookie(CookieConfig.SHOPPING_CART.getName(), cookieValue,
                CookieConfig.SHOPPING_CART.getTtl(), resp);
    }

    public static User getCurrentAccount(HttpServletRequest req) {
        return (User) req.getSession().getAttribute(WebConstants.CURRENT_ACCOUNT);
    }

    public static void setCurrentAccount(HttpServletRequest req, User currentAccount) {
        req.getSession().setAttribute(WebConstants.CURRENT_ACCOUNT, currentAccount);
    }

    public static boolean isCurrentAccountCreated(HttpServletRequest req) {
        return getCurrentAccount(req) != null;
    }

}

