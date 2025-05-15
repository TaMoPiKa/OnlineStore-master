package com.app.onlinestorebackend.DTOs;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GeneralResponseBody<T> {
    private T data;

    public GeneralResponseBody(T data) {
        this.data = data;
    }

    // Getters and setters for data
}
