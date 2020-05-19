package com.babak.servlet.impl;

import com.babak.entity.User;
import com.babak.servlet.AbstractController;
import com.babak.utils.RoutingUtils;
import com.babak.utils.SessionUtils;
import com.babak.utils.constants.PathConstants;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/secured/cabinet")
public class CabinetServlet extends AbstractController {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = SessionUtils.getCurrentAccount(request);
        session.setAttribute("path", PathConstants.UPLOAD_DIR + user.getLogin() + user.getId() + "." + "jpg");
        logger.debug("Avatar was upload");
        RoutingUtils.forwardToPage(PathConstants.CABINET_PATH, request, response);
    }
}
