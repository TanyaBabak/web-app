package com.babak.servlet.impl;

import com.babak.bean.CatalogBean;
import com.babak.bean.FilterFormBean;
import com.babak.entity.Product;
import com.babak.service.CatalogService;
import com.babak.servlet.AbstractController;
import com.babak.utils.ComparatorFilterFormBean;
import com.babak.utils.RoutingUtils;
import com.babak.utils.constants.JspConstants;
import com.babak.utils.constants.PathConstants;
import com.babak.utils.constants.UtilConstants;
import com.babak.utils.constants.WebConstants;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/catalog")
public class CatalogServlet extends AbstractController {


    public CatalogServlet(CatalogService catalogService) {
        setCatalogService(catalogService);
    }

    public CatalogServlet() {
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext context = request.getServletContext();
        Map<String, String> categories = getCatalogService().getAllCategories();
        context.setAttribute(WebConstants.CATEGORIES, categories);
        context.setAttribute(WebConstants.MANUFACTURERS, getCatalogService().getAllManufacturers());
        logger.debug("Request was receive");
        RoutingUtils.forwardToPage(PathConstants.CATALOG_PAGE, request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType(UtilConstants.CONTENT_TYPE1);
        HttpSession session = request.getSession();
        session.setAttribute(JspConstants.PAGE, request.getParameter(JspConstants.PAGE));
        session.setAttribute(JspConstants.RECORDS_PER_PAGE, request.getParameter(JspConstants.RECORDS_PER_PAGE));
        Integer currentPage = Integer.parseInt((String) session.getAttribute(JspConstants.PAGE));
        int recordsPerPage = Integer.parseInt((String) session.getAttribute(JspConstants.RECORDS_PER_PAGE));
        logger.debug("Attribute with session was receive ");
        FilterFormBean filterFormBean = setResponse(request, response);
        boolean comparator = new ComparatorFilterFormBean().compareFilter(filterFormBean);
        List<Product> products = getCatalogService().findProducts(currentPage, recordsPerPage, filterFormBean, comparator);
        logger.debug("Necessary products was get");
        int rows = getCatalogService().getNumberOfRows(comparator, filterFormBean);
        Integer numberPages = rows / recordsPerPage;
        if (numberPages % recordsPerPage > 0) {
            numberPages++;
        }
        request.setAttribute(JspConstants.PRODUCTS, products);
        request.setAttribute("numberPages", String.valueOf(numberPages));
        request.setAttribute("currentPage", currentPage);
        logger.debug(CatalogBean.class + " was shaped");
        RoutingUtils.forwardToFragment("filter-form.jsp", request, response);
    }

    private FilterFormBean setResponse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String minPrice = request.getParameter(JspConstants.MIN_PRICE);
        String maxPrice = request.getParameter(JspConstants.MAX_PRICE);
        String sort = request.getParameter(JspConstants.SORT);
        String[] manufacturers = request.getParameterValues(JspConstants.MANUFACTURE);
        List<String> manufacturersList = null;
        String category = request.getParameter(JspConstants.CATEGORY);
        if (!Objects.isNull(manufacturers)) {
            manufacturersList = Arrays.asList(manufacturers);
        }
        logger.debug("Data with form filter was receive");
        return FilterFormBean.builder()
                .category(category)
                .manufacturer(manufacturersList)
                .maxPrice(Integer.parseInt(maxPrice))
                .minPrice(Integer.parseInt(minPrice))
                .sort(sort).build();
    }
}
