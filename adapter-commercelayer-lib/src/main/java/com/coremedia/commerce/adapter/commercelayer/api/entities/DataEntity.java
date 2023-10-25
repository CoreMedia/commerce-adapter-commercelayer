package com.coremedia.commerce.adapter.commercelayer.api.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DataEntity<T> {

    @JsonProperty("data")
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
