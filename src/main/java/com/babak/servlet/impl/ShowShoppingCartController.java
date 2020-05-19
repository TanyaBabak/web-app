package com.babak.servlet.impl;

import com.babak.servlet.AbstractController;
import com.babak.utils.RoutingUtils;
import com.babak.utils.SessionUtils;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/shopping-cart")
public class ShowShoppingCartController extends AbstractController {

    private static final long serialVersionUID = -1916373553298888514L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        if (SessionUtils.isCurrentShoppingCartCreated(req)) {
            try {
                RoutingUtils.forwardToPage("shopping-cart.jsp", req, resp);
            } catch (ServletException e) {
                logger.error("shopping-cart.jsp error");
            } catch (IOException e) {
                logger.error("shopping-cart.jsp error");
            }
        } else {
            try {
                RoutingUtils.redirect("/catalog", req, resp);
            } catch (IOException e) {
                logger.error("/catalog error");
            }
        }
    }
}
