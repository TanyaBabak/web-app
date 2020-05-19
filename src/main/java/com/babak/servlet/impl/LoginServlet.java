package com.babak.servlet.impl;

import com.babak.entity.User;
import com.babak.servlet.AbstractController;
import com.babak.utils.Hashing;
import com.babak.utils.RoutingUtils;
import com.babak.utils.SessionUtils;
import com.babak.utils.constants.EntityConstants;
import com.babak.utils.constants.JspConstants;
import com.babak.utils.constants.PathConstants;
import com.babak.utils.constants.WebConstants;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends AbstractController {


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.debug("Captcha was create and ready for send");
        RoutingUtils.forwardToPage(PathConstants.LOGIN_JSP_PATH, request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter(JspConstants.LOGIN_LOGIN);
        String passwordInput = request.getParameter(JspConstants.CURRENT_PASSWORD);
        String password = Hashing.md5Apache(passwordInput);
        HttpSession session = request.getSession();
        User user = getUserService().checkUserLoginAndPassword(login, password);
        if (Objects.nonNull(user)) {
            SessionUtils.setCurrentAccount(request, user);
            logger.debug("Redirect in cabinet");
            response.sendRedirect(PathConstants.CABINET_JSP_PATH);
        } else {
            Map<String, String> errors = new HashMap<>();
            errors.put(EntityConstants.LOGIN, "Data is wrong");
            request.setAttribute(WebConstants.ERRORS, errors);
            logger.debug("Error password or login");
            RoutingUtils.forwardToPage(PathConstants.LOGIN_JSP_PATH, request, response);
        }
    }
}
