package com.example.demo.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Order extends DefaultTimeStamps{

    @Id
    @GeneratedValue
    private  Long id;
    @OneToMany(mappedBy = "id")
    private List<Product> products;
    private  Long quantity;
}
