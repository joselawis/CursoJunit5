package com.lawis.junitapp.ejemplo.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class AccountTest {

    @Test
    void testNombreCuenta() {
        Account account = new Account("John Doe", new BigDecimal("1000.12346"));

        String expected = "John Doe";
        String actual = account.getPerson();

        assertEquals(expected, actual);
        assertTrue(() -> actual.equals(expected));
    }

}
