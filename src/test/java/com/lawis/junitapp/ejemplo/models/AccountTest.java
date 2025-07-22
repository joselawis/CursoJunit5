package com.lawis.junitapp.ejemplo.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class AccountTest {

    @Test
    void testPersonAccount() {
        Account account = new Account("John Doe", new BigDecimal("1000.12346"));

        String expected = "John Doe";
        String actual = account.getPerson();

        assertEquals(expected, actual);
        assertTrue(() -> actual.equals(expected));
    }

    @Test
    void testBalanceAccount() {
        Account account = new Account("Jane Doe", new BigDecimal("2000.54321"));

        assertEquals(2000.54321, account.getBalance().doubleValue());
        assertFalse(() -> account.getBalance().compareTo(BigDecimal.ZERO) < 0);
    }

}
