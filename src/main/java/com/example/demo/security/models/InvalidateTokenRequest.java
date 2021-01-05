package com.example.demo.security.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class InvalidateTokenRequest implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;
    private String token;
}
