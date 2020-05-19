package com.babak.servlet.impl;

import com.babak.model.ShoppingCart;
import com.babak.servlet.AbstractProductController;
import com.babak.utils.SessionUtils;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ajax/json/product/add")
public class AddProductToShoppingCart extends AbstractProductController {

    private static final long serialVersionUID = -3046216247699203961L;


    @Override
    protected void processProductForm(int idProduct, int count, ShoppingCart shoppingCart, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getOrderService().addProductToShoppingCart(idProduct, count, shoppingCart);
        String cookieValue = getOrderService().serializeShoppingCart(shoppingCart);
        SessionUtils.updateCurrentShoppingCartCookie(cookieValue, resp);
    }
}
