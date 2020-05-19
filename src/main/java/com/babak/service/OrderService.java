package com.babak.service;

import com.babak.entity.User;
import com.babak.model.ShoppingCart;

public interface OrderService {

    void addProductToShoppingCart(int idProduct, int count, ShoppingCart shoppingCart);

    ShoppingCart deserializeShoppingCart(String string);

    String serializeShoppingCart(ShoppingCart shoppingCart);

    void removeProductFromShoppingCart(int idProduct, int count, ShoppingCart shoppingCart);

    Integer makeOrder(ShoppingCart shoppingCart, User currentAccount);
}
