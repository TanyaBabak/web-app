package com.babak.servlet;

import com.babak.service.CaptchaService;
import com.babak.service.CatalogService;
import com.babak.service.OrderService;
import com.babak.service.UserService;
import com.babak.utils.constants.WebConstants;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractController extends HttpServlet {

    protected final Logger logger = LoggerFactory.getLogger(getClass());
    private CatalogService catalogService;
    private OrderService orderService;
    private CaptchaService captchaService;
    private UserService userService;

    @Override
    public final void init(ServletConfig servletConfig) {
        ServletContext servletContext = servletConfig.getServletContext();
        catalogService = (CatalogService) servletContext.getAttribute(WebConstants.SERVICE_CATALOG);
        orderService = (OrderService) servletContext.getAttribute(WebConstants.SERVICE_ORDER);
        captchaService = (CaptchaService) servletContext.getAttribute(WebConstants.SERVICE_CAPTCHA);
        userService = (UserService) servletContext.getAttribute(WebConstants.SERVICE_USER);
        initServlet(servletConfig);
    }

    public void initServlet(ServletConfig servletConfig) {
    }

    public final CatalogService getCatalogService() {
        return catalogService;
    }

    public final OrderService getOrderService() {
        return orderService;
    }

    public final CaptchaService getCaptchaService() {
        return captchaService;
    }

    public final UserService getUserService() {
        return userService;
    }

    public void setCatalogService(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    public void setCaptchaService(CaptchaService captchaService) {
        this.captchaService = captchaService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
