package com.example.demo.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Orders extends DefaultTimeStamps {

    @Id
    @GeneratedValue
    private Long id;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Items> itemsList;
    @ManyToOne(targetEntity = OrderStatus.class,cascade = CascadeType.ALL)
    private OrderStatus orderStatus;
    @ManyToOne(targetEntity = Address.class,optional = false,cascade = CascadeType.REFRESH)
    private Address address;
    @ManyToOne(targetEntity = User.class,optional = false,cascade = CascadeType.REFRESH)
    private User user;
}
