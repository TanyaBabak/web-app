package com.babak.filter;

import com.babak.utils.RoutingUtils;
import com.babak.utils.SessionUtils;
import com.babak.utils.UrlUtils;
import com.babak.utils.WebUtils;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter(filterName = "CheckAuthenticationFilter")
public class CheckAuthenticationFilter extends AbstractFilter {

    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
        if (SessionUtils.isCurrentAccountCreated(req)) {
            chain.doFilter(req, resp);
        } else {
            String requestUrl = WebUtils.getCurrentRequestUrl(req);
            if (UrlUtils.isAjaxUrl(requestUrl)) {
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                RoutingUtils.sendHtmlFragment("401", req, resp);
            } else {
                RoutingUtils.redirect("/login", req, resp);
            }
        }
    }
}
