package com.app.onlinestorebackend.entities;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.validation.constraints.NotNull;
import java.util.List;


@Data
@Entity
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Item {

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
    @NotNull
    private String description;
    @NotNull
    @ColumnDefault("1")
    private ItemStatus itemStatus;


}
