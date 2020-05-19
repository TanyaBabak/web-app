package com.babak.service;

import com.babak.bean.FilterFormBean;
import com.babak.entity.Manufacture;
import com.babak.entity.Product;
import java.util.List;
import java.util.Map;

public interface CatalogService {

    List<Product> findProducts(Integer currentPage, Integer recordsPerPage, FilterFormBean filterFormBean, boolean counter);

    int getNumberOfRows(boolean comparator, FilterFormBean filter);

    Map<String, String> getAllCategories();

    List<Manufacture> getAllManufacturers();

}
