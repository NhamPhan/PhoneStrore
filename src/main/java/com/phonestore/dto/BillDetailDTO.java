package com.phonestore.dto;

import com.phonestore.entity.BillDetail;
import com.phonestore.entity.Product;
import com.phonestore.util.DataUtil;

public class BillDetailDTO {

    private Integer id;
    private Integer billId;
    private Integer bookId;
    private Float price;
    private Float subtotal;
    private Integer quantity;
    private Product product;

    public BillDetailDTO(BillDetail billDetail, Product product) {
        this.id = billDetail.getId();
        this.billId = billDetail.getBillId();
        this.bookId = billDetail.getProductId();
        this.price = billDetail.getPrice();
        this.subtotal = billDetail.getSubtotal();
        this.quantity = billDetail.getQuantity();
        this.product = product;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBillId() {
        return billId;
    }

    public void setBillId(Integer billId) {
        this.billId = billId;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Float subtotal) {
        this.subtotal = subtotal;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getPriceVnd() {
        return DataUtil.formatCurrency(this.price);
    }

    public String getSubtotalVnd() {
        return DataUtil.formatCurrency(this.subtotal);
    }
}