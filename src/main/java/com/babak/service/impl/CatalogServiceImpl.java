package com.babak.service.impl;

import com.babak.bean.FilterFormBean;
import com.babak.entity.Manufacture;
import com.babak.entity.Product;
import com.babak.repository.CatalogRepository;
import com.babak.service.CatalogService;
import com.babak.utils.connection.Transaction;
import java.util.List;
import java.util.Map;

public class CatalogServiceImpl implements CatalogService {

    private Transaction transaction;
    private CatalogRepository catalogRepository;

    public CatalogServiceImpl(Transaction transaction, CatalogRepository catalogRepository) {
        this.transaction = transaction;
        this.catalogRepository = catalogRepository;
    }

    @Override
    public List<Product> findProducts(Integer currentPage, Integer recordsPerPage, FilterFormBean filterFormBean, boolean counter) {
        return transaction.executeTransaction(connection -> catalogRepository
                .findProducts(connection, currentPage, recordsPerPage, filterFormBean, counter));
    }

    @Override
    public int getNumberOfRows(boolean comparator, FilterFormBean filter) {
        return transaction.executeTransaction(connection -> catalogRepository
                .getNumberOfRows(connection, comparator, filter));
    }

    @Override
    public Map<String, String> getAllCategories() {
        return transaction.executeTransaction(connection -> catalogRepository
                .getAllCategories(connection));
    }

    @Override
    public List<Manufacture> getAllManufacturers() {
        return transaction.executeTransaction(connection -> catalogRepository
                .getAllManufacturers(connection));
    }
}
