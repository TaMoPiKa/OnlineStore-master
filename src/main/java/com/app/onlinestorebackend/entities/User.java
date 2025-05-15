package com.app.onlinestorebackend.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class User {

    private String name;

    @Id
    private String username;
    @NotNull
    private String password;
    @NotNull
    private char isAdmin;

    @OneToMany
    private List<Order> ordersHistory;

    @OneToMany
    private List<CartItem> cart;



}
