package com.babak.utils;

import com.babak.bean.CatalogBean;
import com.babak.utils.constants.UtilConstants;
import com.google.gson.Gson;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

public class RoutingUtils {

    public static void forwardToFragment(String jspFragment, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jspf/" + jspFragment).forward(req, resp);
    }

    public static void forwardToPage(String jspPage, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/" + jspPage).forward(req, resp);
    }

    public static void sendHtmlFragment(String text, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType(UtilConstants.CONTENT_TYPE1);
        resp.getWriter().println(text);
        resp.getWriter().close();
    }

    public static void sendJson(JSONObject json, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType(UtilConstants.CONTENT_TYPE2);
        resp.getWriter().println(json.toString());
        resp.getWriter().close();
    }

    public static void sendGson(CatalogBean catalogBean, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Gson g = new Gson();
        resp.setContentType(UtilConstants.CONTENT_TYPE2);
        resp.getWriter().write(g.toJson(catalogBean));
        resp.getWriter().close();
    }

    public static void redirect(String url, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect(url);
    }
}
