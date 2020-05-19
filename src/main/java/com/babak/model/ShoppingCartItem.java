package com.babak.model;

import com.babak.entity.Product;
import java.io.Serializable;
import java.math.BigDecimal;

public final class ShoppingCartItem implements Serializable {

    private static final long serialVersionUID = 6436798264138502851L;

    private Product product;
    private int count;
    private BigDecimal price;

    public ShoppingCartItem() {
        super();
    }

    public ShoppingCartItem(Product product, int count, BigDecimal price) {
        super();
        this.product = product;
        this.count = count;
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return String.format("ShoppingCartItem [product=%s, count=%s]", product, count);
    }
}

