package com.app.onlinestorebackend.DTOs.Requests;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class AddNewItemToCartRequest {


    private String itemId;
    private String username;
    private int quantity;
}
