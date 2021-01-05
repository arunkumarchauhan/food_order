package com.example.demo.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Getter
@Setter
public class OrderStatus extends DefaultTimeStamps {
   @Id
   @GeneratedValue
    private Long id;
    @Builder.Default
    private String status="placed";

}
