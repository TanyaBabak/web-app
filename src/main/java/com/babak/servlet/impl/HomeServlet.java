package com.babak.servlet.impl;

import com.babak.servlet.AbstractController;
import com.babak.utils.RoutingUtils;
import com.babak.utils.constants.PathConstants;
import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/home")
public class HomeServlet extends AbstractController {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.debug("User on home page");
        System.out.print("default: ");
        System.out.println(Locale.getDefault());
        System.out.println("LOCALE IN SERVLET " + request.getLocale());
        Enumeration<Locale> localeEnumeration = request.getLocales();
        List<Locale> localeList = Collections.list(localeEnumeration);
        localeList.forEach(n -> System.out.println("locales: " + n));
        RoutingUtils.forwardToPage(PathConstants.HOME_PATH_TAG, request, response);
    }
}
