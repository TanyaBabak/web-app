package com.babak.model;

import com.babak.entity.Product;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class ShoppingCart {

    private Map<Integer, ShoppingCartItem> itemCart = new LinkedHashMap<>();
    private int totalCount;
    private BigDecimal totalCost = BigDecimal.ZERO;

    public void addProductToShoppingCart(Product product, int count) {
        int idProduct = product.getId();
        ShoppingCartItem shoppingCartItem = itemCart.get(idProduct);
        if (Objects.isNull(shoppingCartItem)) {
            shoppingCartItem = new ShoppingCartItem(product, count, product.getPrice());
            itemCart.put(idProduct, shoppingCartItem);
        } else {
            shoppingCartItem.setCount(shoppingCartItem.getCount() + count);
        }
        refreshStatistics();
    }

    public void removeProduct(Integer idProduct, int count) {
        ShoppingCartItem shoppingCartItem = itemCart.get(idProduct);
        if (shoppingCartItem != null) {
            if (shoppingCartItem.getCount() > count) {
                shoppingCartItem.setCount(shoppingCartItem.getCount() - count);
            } else {
                itemCart.remove(idProduct);
            }
            refreshStatistics();
        }
    }

    public int getTotalCount() {
        return totalCount;
    }

    public Collection<ShoppingCartItem> getItems() {
        return itemCart.values();
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    private void refreshStatistics() {
        totalCount = 0;
        totalCost = BigDecimal.ZERO;
        for (ShoppingCartItem shoppingCartItem : getItems()) {
            totalCount += shoppingCartItem.getCount();
            totalCost = totalCost.add(shoppingCartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(shoppingCartItem.getCount())));
        }
    }

}
