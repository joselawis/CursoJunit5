package com.lawis.junitapp.ejemplo.models;

import java.math.BigDecimal;

import com.lawis.junitapp.ejemplo.exceptions.NotEnoughMoneyException;

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

    private Bank bank;

    public void debit(BigDecimal amount) throws NotEnoughMoneyException {
        BigDecimal newBalance = this.balance.subtract(amount);
        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new NotEnoughMoneyException("Not enough money in the account");
        }
        this.balance = newBalance;
    }

    public void credit(BigDecimal amount) {
        this.balance = this.balance.add(amount);
    }

}
