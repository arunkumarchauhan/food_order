package com.example.demo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "InvalidTokens")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class InvalidTokens extends DefaultTimeStamps {
    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    private String token;
}
