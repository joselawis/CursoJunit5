package com.lawis.junitapp.ejemplo.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class Bank {

    @NonNull
    private String name;

    private List<Account> accounts = new ArrayList<>();

    public void transfer(Account from, Account to, BigDecimal amount) {
        from.debit(amount);
        to.credit(amount);
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

}
