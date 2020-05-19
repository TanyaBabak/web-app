package com.babak.servlet.impl;

import com.babak.exception.InternalServerErrorException;
import com.babak.servlet.AbstractController;
import com.babak.utils.constants.PathConstants;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/logout")
public class LogoutServlet extends AbstractController {

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        httpServletRequest.getSession().invalidate();
        try {
            httpServletResponse.sendRedirect(PathConstants.HOME_UTL);
        } catch (IOException e) {
            logger.error("Exception occurred : {}", e.getMessage());
            throw new InternalServerErrorException("Page home unavailable", e);
        }
    }
}
