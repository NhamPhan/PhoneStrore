package com.phonestore.constant;

public enum StatusBookEnum {

    ACTIVE(0), INACTIVE(1);

    Integer status;
    StatusBookEnum(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }
}