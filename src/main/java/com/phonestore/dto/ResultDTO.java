package com.phonestore.dto;

public class ResultDTO<T> {

    private String message;
    private boolean isError;
    private T data;

    public ResultDTO() {}

    public ResultDTO(T data, boolean isError, String message) {
        this.message = message;
        this.isError = isError;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return new StringBuilder("{")
                .append("\"isError\": ").append(this.isError)
                .append(",")
                .append("\"message\": ").append(this.message)
                .append("}")
                .toString();

    }
}