package com.example.demo.security.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class InvalidateTokenResponse implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;
    private String response;
}
