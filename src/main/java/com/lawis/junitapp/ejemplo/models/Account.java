package com.lawis.junitapp.ejemplo.models;

import java.math.BigDecimal;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode
public class Account {
    @NonNull
    private String person;
    @NonNull
    private BigDecimal balance;

}
