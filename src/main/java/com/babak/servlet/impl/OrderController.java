package com.babak.servlet.impl;

import com.babak.model.ShoppingCart;
import com.babak.servlet.AbstractController;
import com.babak.utils.SessionUtils;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/order")
public class OrderController extends AbstractController {
    private static final long serialVersionUID = -1782066337808445826L;
    private static final String CURRENT_MESSAGE = "CURRENT_MESSAGE";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ShoppingCart shoppingCart = SessionUtils.getCurrentShoppingCart(req);
        int idOrder = getOrderService().makeOrder(shoppingCart, SessionUtils.getCurrentAccount(req));
        SessionUtils.clearCurrentShoppingCart(req, resp);
        req.getSession().setAttribute(CURRENT_MESSAGE, "Order created successfully. Please wait for our reply.");
    }
}
