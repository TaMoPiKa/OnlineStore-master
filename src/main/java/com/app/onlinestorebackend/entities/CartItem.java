package com.app.onlinestorebackend.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import javax.validation.constraints.NotNull;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class CartItem {

    @Id
    private String id;
    @NotNull
    private String name;
    @NotNull
    private int quantity;
    @NotNull
    private double unitPrice;
    @NotNull
    private String image;


}
