package com.babak.filter;

import com.babak.model.ShoppingCart;
import com.babak.service.OrderService;
import com.babak.utils.SessionUtils;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter(filterName = "AutoRestoreShoppingCartFilter")
public class AutoRestoreShoppingCartFilter extends AbstractFilter {
    private static final String SHOPPING_CARD_DESERIALIZATION_DONE = "SHOPPING_CARD_DESERIALIZATION_DONE";

    private OrderService orderService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        orderService = (OrderService) filterConfig.getServletContext().getAttribute("serviceOrder");
    }

    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
        if (req.getSession().getAttribute(SHOPPING_CARD_DESERIALIZATION_DONE) == null) {
            if (!SessionUtils.isCurrentShoppingCartCreated(req)) {
                Cookie cookie = SessionUtils.findShoppingCartCookie(req);
                if (cookie != null) {
                    ShoppingCart shoppingCart = orderService.deserializeShoppingCart(cookie.getValue());
                    if (shoppingCart != null) {
                        SessionUtils.setCurrentShoppingCart(req, shoppingCart);
                    }
                }
            }
            req.getSession().setAttribute(SHOPPING_CARD_DESERIALIZATION_DONE, Boolean.TRUE);
        }
        chain.doFilter(req, resp);
    }
}
