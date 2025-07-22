package com.lawis.junitapp.ejemplo.models;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Account {
    private String person;
    private BigDecimal balance;

}
