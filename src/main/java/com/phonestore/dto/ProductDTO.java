package com.phonestore.dto;

public class ProductDTO {

    private String name;
    private Integer idCategory;
    private Float fromPrice;
    private Float toPrice;

    public ProductDTO() {}

    public ProductDTO(String name, Integer idCategory, Float fromPrice, Float toPrice) {
        this.name = name;
        this.idCategory = idCategory;
        this.fromPrice = fromPrice;
        this.toPrice = toPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(Integer idCategory) {
        this.idCategory = idCategory;
    }

    public Float getFromPrice() {
        return fromPrice;
    }

    public void setFromPrice(Float fromPrice) {
        this.fromPrice = fromPrice;
    }

    public Float getToPrice() {
        return toPrice;
    }

    public void setToPrice(Float toPrice) {
        this.toPrice = toPrice;
    }
}