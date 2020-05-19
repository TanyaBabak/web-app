package com.babak.servlet.impl;

import com.babak.servlet.AbstractController;
import com.babak.utils.RoutingUtils;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/error")
public class ErrorController extends AbstractController {
    private static final long serialVersionUID = -4385792519039493271L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("statusCode", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        RoutingUtils.forwardToPage("error.jsp", req, resp);
    }
}
