package com.phonestore.dto;

import com.phonestore.entity.Product;
import com.phonestore.entity.Cart;
import com.phonestore.util.DataUtil;

public class CartDTO {

    private Integer id;
    private Product product;
    private Integer quantity;
    private String username;

    public CartDTO() {}

    public CartDTO(Cart cart) {
        this(cart.getId(), null, cart.getQuantity(), cart.getUsername());
    }

    public CartDTO(Integer id, Product product, Integer quantity, String username) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.username = username;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPriceVnd() {
        return DataUtil.formatCurrency(this.product.getPrice());
    }

    public String getSubtotalVnd() {
        return DataUtil.formatCurrency(this.product.getPrice() * this.quantity);
    }

    public float getSubtotal() {
        return this.product.getPrice() * this.quantity;
    }
}