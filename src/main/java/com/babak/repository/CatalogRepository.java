package com.babak.repository;

import com.babak.bean.FilterFormBean;
import com.babak.entity.Manufacture;
import com.babak.entity.Product;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

public interface CatalogRepository {

    List<Product> findProducts(Connection connection, Integer currentPage,
                               Integer recordsPerPage, FilterFormBean filterFormBean, boolean counter);

    int getNumberOfRows(Connection connection, boolean comparator, FilterFormBean filter);

    Map<String, String> getAllCategories(Connection connection);

    List<Manufacture> getAllManufacturers(Connection connection);
}
