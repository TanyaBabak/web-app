package com.babak.repository;

import com.babak.entity.Product;
import com.babak.entity.User;
import com.babak.model.ShoppingCartItem;
import java.sql.Connection;
import java.util.Collection;

public interface OrderRepository {

    Product addProductToShoppingCart(int id, Connection connection);

    Integer makeOrder(Connection connection, User currentAccount);

    Boolean insertItem(Collection<ShoppingCartItem> items, Connection connection, int idOrder);
}
