package com.babak.repository.impl;

import com.babak.entity.Order;
import com.babak.entity.Product;
import com.babak.entity.Status;
import com.babak.entity.User;
import com.babak.exception.InternalServerErrorException;
import com.babak.model.ShoppingCartItem;
import com.babak.repository.OrderRepository;
import com.babak.utils.constants.SqlQuery;
import com.babak.utils.constants.UtilConstants;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrderRepositoryImpl implements OrderRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderRepositoryImpl.class);

    @Override
    public Product addProductToShoppingCart(int idProduct, Connection connection) {
        Product product = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SqlQuery.PRODUCT_BY_ID)) {
            preparedStatement.setInt(1, idProduct);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                product = new Product();
                product.setProductName(resultSet.getString("product_name"));
                product.setPrice(resultSet.getBigDecimal("price"));
                product.setProductDescription(resultSet.getString("product_description"));
                product.setLink(resultSet.getString("link"));
                product.setId(idProduct);
            }
        } catch (SQLException e) {
            LOGGER.error("Failed to find product to database. Exception occurred : {}", e.getMessage());
        }
        return product;
    }

    @Override
    public Integer makeOrder(Connection connection, User currentAccount) {
        int result = 0;
        Order order = new Order();
        try (PreparedStatement preparedStatement = connection
                .prepareStatement(SqlQuery.INSERT_ORDER, Statement.RETURN_GENERATED_KEYS)) {
            int i = 1;
            preparedStatement.setString(i++, Status.ACCEPTED.getDescription());
            preparedStatement.setString(i++, UtilConstants.DESCRIPTION_STATUS);
            preparedStatement.setInt(i++, currentAccount.getId());
            preparedStatement.setTimestamp(i, new Timestamp(System.currentTimeMillis()));
            result = preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                order.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            throw new InternalServerErrorException("Can't execute SQL request: " + e.getMessage(), e);
        }
        if (result > 0) {
            return order.getId();
        } else {
            return null;
        }
    }

    @Override
    public Boolean insertItem(Collection<ShoppingCartItem> items, Connection connection, int idOrder) {
        try (
                PreparedStatement statement = connection.prepareStatement(SqlQuery.INSERT_ORDER_ITEM);) {
            int count = 0;
            for (ShoppingCartItem item : items) {
                statement.setLong(1, idOrder);
                statement.setInt(2, item.getProduct().getId());
                statement.setInt(3, item.getCount());

                statement.addBatch();
                count++;
                if (count % 100 == 0 || count == items.size()) {
                    statement.executeBatch();
                    return Boolean.TRUE;
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return Boolean.FALSE;
    }
}
