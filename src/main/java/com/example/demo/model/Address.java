package com.example.demo.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Getter
@Setter
@Builder
public class Address {
    @Id
    @GeneratedValue
    private Long id;
    @Size(max = 255)
    private String addressLine1;
    @Size(max = 255)
    private String addressLine2;
    @Size(max = 100)
    private String city;
    @Size(max = 100)
    private String zipcode;
    @Size(max = 100)
    private String country;
    @Builder.Default
    private Boolean isPrimaryAddress = false;
}
