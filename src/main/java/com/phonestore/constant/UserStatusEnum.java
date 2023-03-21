package com.phonestore.constant;

public enum UserStatusEnum {

    ACTIVE(1), INACTIVE(0);

    Integer value;

    UserStatusEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
