package com.babak.service.impl;

import com.babak.entity.Product;
import com.babak.entity.User;
import com.babak.exception.InternalServerErrorException;
import com.babak.exception.ValidationException;
import com.babak.model.ShoppingCart;
import com.babak.model.ShoppingCartItem;
import com.babak.repository.OrderRepository;
import com.babak.service.OrderService;
import com.babak.utils.connection.Transaction;
import java.util.Collection;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrderServiceImpl implements OrderService {

    private Transaction transaction;
    private OrderRepository orderRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

    public OrderServiceImpl(Transaction transaction, OrderRepository orderRepository) {
        this.transaction = transaction;
        this.orderRepository = orderRepository;
    }

    @Override
    public void addProductToShoppingCart(int idProduct, int count, ShoppingCart shoppingCart) {
        Product product = transaction.executeTransaction(connection ->
                orderRepository.addProductToShoppingCart(idProduct, connection));
        if (Objects.isNull(product)) {
            throw new ValidationException("Product not found by id= " + idProduct);
        }
        shoppingCart.addProductToShoppingCart(product, count);
    }

    @Override
    public void removeProductFromShoppingCart(int idProduct, int count, ShoppingCart shoppingCart) {
        shoppingCart.removeProduct(idProduct, count);
    }

    @Override
    public ShoppingCart deserializeShoppingCart(String string) {
        ShoppingCart shoppingCart = new ShoppingCart();
        String[] items = string.split("\\|");
        for (String item : items) {
            try {
                String[] data = item.split("-");
                int idProduct = Integer.parseInt(data[0]);
                int count = Integer.parseInt(data[1]);
                addProductToShoppingCart(idProduct, count, shoppingCart);
            } catch (RuntimeException e) {
                LOGGER.error("Can't add product to ShoppingCart during deserialization: item=" + item, e);
            }
        }
        return shoppingCart.getItems().isEmpty() ? null : shoppingCart;
    }

    @Override
    public String serializeShoppingCart(ShoppingCart shoppingCart) {
        StringBuilder res = new StringBuilder();
        for (ShoppingCartItem item : shoppingCart.getItems()) {
            res.append(item.getProduct().getId()).append("-").append(item.getCount()).append("|");
        }
        if (res.length() > 0) {
            res.deleteCharAt(res.length() - 1);
        }
        return res.toString();
    }

    @Override
    public Integer makeOrder(ShoppingCart shoppingCart, User currentAccount) {
        if (shoppingCart == null || shoppingCart.getItems().isEmpty()) {
            throw new InternalServerErrorException("shoppingCart is null or empty");
        }
        Integer idOrder = transaction.executeTransaction(connection -> orderRepository.makeOrder(connection, currentAccount));
        if (Objects.isNull(idOrder)) {
            return null;
        }
        Collection<ShoppingCartItem> items = shoppingCart.getItems();
        transaction.executeTransaction(connection -> orderRepository.insertItem(items, connection, idOrder));
        return idOrder;
    }
}
