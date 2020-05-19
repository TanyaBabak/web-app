package com.babak.repository.impl;

import com.babak.bean.FilterFormBean;
import com.babak.entity.Category;
import com.babak.entity.Manufacture;
import com.babak.entity.Product;
import com.babak.exception.InternalServerErrorException;
import com.babak.repository.CatalogRepository;
import com.babak.utils.constants.EntityConstants;
import com.babak.utils.constants.SqlQuery;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CatalogRepositoryImpl implements CatalogRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(CatalogRepositoryImpl.class);
    private Map<String, String> categories = new LinkedHashMap<>();

    @Override
    public List<Product> findProducts(Connection connection, Integer currentPage, Integer recordsPerPage,
                                      FilterFormBean filter, boolean comparator) {
        if (comparator) {
            return findWithoutFilter(connection, currentPage, recordsPerPage);
        }
        List<Product> products = null;
        int start = currentPage * recordsPerPage - recordsPerPage;
        String category = getSqlForCategory(filter.getCategory());
        List<String> manufacturers = filter.getManufacturer();
        String sql = SqlQuery.FILTER + category
                + getSqlForManufacturer(filter.getManufacturer()) + SqlQuery.PRICE
                + SqlQuery.ORDER_BY + getSqlForOrderBy(filter.getSort()) + SqlQuery.LIMIT;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            int i = 1;
            if (!category.equals("")) {
                preparedStatement.setString(i++, filter.getCategory());
            }
            if (!Objects.isNull(manufacturers)) {
                for (String manufacturer : manufacturers) {
                    preparedStatement.setString(i++, manufacturer);
                }
            }
            preparedStatement.setInt(i++, filter.getMinPrice());
            preparedStatement.setInt(i++, filter.getMaxPrice());
            preparedStatement.setInt(i++, start);
            preparedStatement.setInt(i, recordsPerPage);
            ResultSet resultSet;
            resultSet = preparedStatement.executeQuery();
            products = new ArrayList<>();
            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getInt(EntityConstants.ID));
                product.setProductName(resultSet.getString(EntityConstants.PRODUCT_NAME));
                product.setPrice(resultSet.getBigDecimal(EntityConstants.PRODUCT_PRICE));
                product.setProductDescription(resultSet.getString(EntityConstants.PRODUCT_DESCRIPTION));
                String name = resultSet.getString(EntityConstants.PRODUCT_LINK);
                product.setLink(name);
                products.add(product);
            }
        } catch (SQLException e) {
            LOGGER.error("Failed to find products to database. Exception occurred : {}", e.getMessage());
            throw new InternalServerErrorException("Can't execute sql query: " + e.getMessage(), e);
        }
        return products;
    }


    @Override
    public int getNumberOfRows(Connection connection, boolean comparator, FilterFormBean filter) {
        ResultSet resultSet;
        int result = 0;
        try (Statement statement = connection.createStatement()) {
            if (comparator) {
                resultSet = statement.executeQuery(SqlQuery.NUMBER_ROWS);
            } else {
                resultSet = statement.executeQuery(SqlQuery.COUNT_BY_FILTER_HEADER + SqlQuery.FILTER + getSqlForCategory(filter.getCategory())
                        + getSqlForManufacturer(filter.getManufacturer()) + SqlQuery.PRICE + SqlQuery.COUNT_BY_FILTER_FOOTER);
            }
            resultSet.next();
            result = resultSet.getInt(SqlQuery.COUNT_ALL);
        } catch (SQLException e) {
            LOGGER.error("Failed to find count products in database. Exception occurred : {}", e.getMessage());
            throw new InternalServerErrorException("Can't execute sql query: " + e.getMessage(), e);
        }
        return result;
    }

    @Override
    public Map<String, String> getAllCategories(Connection connection) {
        ResultSet resultSet;
        Category category;
        try (Statement statement = connection.createStatement()) {
            resultSet = statement.executeQuery(SqlQuery.GET_ALL_CATEGORIES);
            while (resultSet.next()) {
                category = new Category();
                category.setId(resultSet.getInt(EntityConstants.ID));
                category.setCategoryName(resultSet.getString(EntityConstants.CATEGORY_NAME));
                String name = category.getCategoryName();
                categories.put(name, SqlQuery.FIELD_CATEGORY_NAME + name);
            }
        } catch (SQLException e) {
            LOGGER.error("Failed to show categories with database. Exception occurred : {}", e.getMessage());
            throw new InternalServerErrorException("Can't execute sql query: " + e.getMessage(), e);
        }
        return categories;
    }

    @Override
    public List<Manufacture> getAllManufacturers(Connection connection) {
        ResultSet resultSet;
        Manufacture manufacture;
        List<Manufacture> manufacturers = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            resultSet = statement.executeQuery(SqlQuery.GET_ALL_MANUFACTURERS);
            while (resultSet.next()) {
                manufacture = new Manufacture();
                manufacture.setId(resultSet.getInt(EntityConstants.ID));
                manufacture.setManufactureName(resultSet.getString(EntityConstants.MANUFACTURE_NAME));
                manufacturers.add(manufacture);
            }
        } catch (SQLException e) {
            LOGGER.error("Failed to show manufacturers with database. Exception occurred : {}", e.getMessage());
            throw new InternalServerErrorException("Can't execute sql query: " + e.getMessage(), e);
        }
        return manufacturers;
    }

    private String getSqlForManufacturer(List<String> manufacturers) {
        String partOfQuery;
        if (!Objects.isNull(manufacturers)) {
            StringBuilder stringBuilder = new StringBuilder();
            int countManufacturer = manufacturers.size();
            stringBuilder.append(SqlQuery.MANUFACTURE_NAME_IN);
            for (int i = 0; i < countManufacturer; i++) {
                stringBuilder.append("?,");
            }
            String strQuery = stringBuilder.toString();
            partOfQuery = strQuery.substring(0, strQuery.length() - 1);
            partOfQuery += ") AND";
        } else {
            partOfQuery = "";
        }
        return partOfQuery;
    }

    private String getSqlForCategory(String category) {
        for (Map.Entry<String, String> categorySet : categories.entrySet()) {
            if (categorySet.getKey().equals(category)) {
                return categorySet.getValue();
            }
        }
        return " ";
    }

    private String getSqlForOrderBy(String sort) {
        String[] strSort = sort.split("_");
        if (strSort.length == 2) {
            return strSort[0] + " " + strSort[1];
        }
        return strSort[0] + "_" + strSort[1] + " " + strSort[2];
    }

    private List<Product> findWithoutFilter(Connection connection, Integer currentPage, Integer recordsPerPage) {
        List<Product> products = null;
        int start = currentPage * recordsPerPage - recordsPerPage;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SqlQuery.SELECT_ALL_PRODUCTS)) {
            preparedStatement.setInt(1, start);
            preparedStatement.setInt(2, recordsPerPage);
            ResultSet resultSet;
            resultSet = preparedStatement.executeQuery();
            products = new ArrayList<>();
            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getInt(EntityConstants.ID));
                product.setProductName(resultSet.getString(EntityConstants.PRODUCT_NAME));
                product.setPrice(resultSet.getBigDecimal(EntityConstants.PRODUCT_PRICE));
                product.setProductDescription(resultSet.getString(EntityConstants.PRODUCT_DESCRIPTION));
                String name = resultSet.getString(EntityConstants.PRODUCT_LINK);
                product.setLink(name);
                products.add(product);
            }
        } catch (SQLException e) {
            LOGGER.error("Failed to find all products in database. Exception occurred : {}", e.getMessage());
            throw new InternalServerErrorException("Can't execute sql query: " + e.getMessage(), e);
        }
        return products;
    }
}
