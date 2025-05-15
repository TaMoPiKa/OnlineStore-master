package com.app.onlinestorebackend.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DialectOverride;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "orders")
public class Order {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderNumber;

    @ManyToOne
    private User buyer;

    @OneToMany
    private List<CartItem> items;

    private LocalDate date;

    private LocalTime time;

    private double total;

    private String status;

    @ColumnDefault("0")
    private PaymentMethod paymentMethod;

    public Order(User buyer, List<CartItem> items, LocalDate date, LocalTime time, double total, String status, PaymentMethod paymentMethod) {
        this.buyer = buyer;
        this.items = items;
        this.date = date;
        this.time = time;
        this.total = total;
        this.status = status;
        this.paymentMethod = paymentMethod;
    }
}
