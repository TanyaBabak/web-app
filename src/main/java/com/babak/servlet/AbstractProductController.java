package com.babak.servlet;

import com.babak.model.ShoppingCart;
import com.babak.utils.RoutingUtils;
import com.babak.utils.SessionUtils;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

public abstract class AbstractProductController extends AbstractController {

    private static final long serialVersionUID = 5096979151346608146L;

    @Override
    protected final void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int itProduct = Integer.parseInt(req.getParameter("idProduct"));
        int count = Integer.parseInt(req.getParameter("count"));
        ShoppingCart shoppingCart = getCurrentShoppingCart(req);
        processProductForm(itProduct, count, shoppingCart, req, resp);
        if (!SessionUtils.isCurrentShoppingCartCreated(req)) {
            SessionUtils.setCurrentShoppingCart(req, shoppingCart);
        }
        sendResponse(shoppingCart, req, resp);
    }

    private ShoppingCart getCurrentShoppingCart(HttpServletRequest req) {
        ShoppingCart shoppingCart = SessionUtils.getCurrentShoppingCart(req);
        if (shoppingCart == null) {
            shoppingCart = new ShoppingCart();
        }
        return shoppingCart;
    }

    protected abstract void processProductForm(int idProduct, int count, ShoppingCart shoppingCart, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException;

    protected void sendResponse(ShoppingCart shoppingCart, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        JSONObject cardStatistics = new JSONObject();
        cardStatistics.put("totalCount", shoppingCart.getTotalCount());
        cardStatistics.put("totalCost", shoppingCart.getTotalCost());
        RoutingUtils.sendJson(cardStatistics, req, resp);
    }
}