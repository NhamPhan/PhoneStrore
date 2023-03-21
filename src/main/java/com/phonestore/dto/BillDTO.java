package com.phonestore.dto;

import com.phonestore.entity.Bill;
import com.phonestore.util.DataUtil;
import com.phonestore.util.DateUtil;

import java.util.Date;
import java.util.List;

public class BillDTO {

    private Integer id;
    private String username;
    private Date createdDate;
    private Float total;
    private String discountCode;
    private String paymentMethod;
    private String address;
    private String phone;
    private List<BillDetailDTO> billDetailDTOS;

    public BillDTO(Bill bill, List<BillDetailDTO> billDetailDTOS) {
        this.id = bill.getId();
        this.username = bill.getUsername();
        this.createdDate = bill.getCreatedDate();
        this.total = bill.getTotal();
        this.discountCode = bill.getDiscountCode();
        this.billDetailDTOS = billDetailDTOS;
        this.paymentMethod = bill.getPaymentMethod();
        this.phone = bill.getPhone();
        this.address = bill.getAddress();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public List<BillDetailDTO> getBillDetailDTOS() {
        return billDetailDTOS;
    }

    public void setBillDetailDTOS(List<BillDetailDTO> billDetailDTOS) {
        this.billDetailDTOS = billDetailDTOS;
    }

    public String getFormattedDate() {
        return DateUtil.parse(this.createdDate);
    }

    public String getTotalVnd() {
        return DataUtil.formatCurrency(this.total);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}