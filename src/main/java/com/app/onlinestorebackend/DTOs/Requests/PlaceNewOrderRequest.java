package com.app.onlinestorebackend.DTOs.Requests;


import com.app.onlinestorebackend.entities.CartItem;
import com.app.onlinestorebackend.entities.PaymentMethod;
import com.app.onlinestorebackend.entities.User;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class PlaceNewOrderRequest {


    private String buyerId;

    private PaymentMethod paymentMethod;


}
