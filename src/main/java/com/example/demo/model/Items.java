package com.example.demo.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Items {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne()
    private Product product;
    @Min(value = 1,message = "Quantity cannot be less than 1")
    @NotNull(message = "Quantity cannot be null")
    private Integer quantity;
}
