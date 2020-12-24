package com.example.demo.model;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"email"}))
public class User extends DefaultTimeStamps {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String email;
    private String password;
}
