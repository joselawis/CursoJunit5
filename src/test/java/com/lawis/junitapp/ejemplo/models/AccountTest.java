package com.lawis.junitapp.ejemplo.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import com.lawis.junitapp.ejemplo.exceptions.NotEnoughMoneyException;

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
        assertNotNull(account.getBalance());
        assertFalse(() -> account.getBalance().compareTo(BigDecimal.ZERO) < 0);
        assertTrue(() -> account.getBalance().compareTo(BigDecimal.ZERO) > 0);
    }

    @Test
    void testReferenceAccount() {
        Account account1 = new Account("John Doe", new BigDecimal("1000.12346"));
        Account account2 = new Account("John Doe", new BigDecimal("1000.12346"));

        // assertNotEquals(account2, account1);
        assertEquals(account2, account1);
    }

    @Test
    void testDebitAccount() {
        Account account = new Account("John Doe", new BigDecimal("1000.12346"));
        account.debit(new BigDecimal("100.00"));

        assertNotNull(account.getBalance());
        assertEquals(900, account.getBalance().intValue());
        assertEquals("900.12346", account.getBalance().toPlainString());
    }

    @Test
    void testDebitNotEnoughMoneyException() {
        Account account = new Account("John Doe", new BigDecimal("1000.12346"));

        Exception exception = assertThrows(NotEnoughMoneyException.class,
                () -> account.debit(new BigDecimal("1100.00")));

        String actualMessage = exception.getMessage();
        String expectedMessage = "Not enough money in the account";
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testCreditAccount() {
        Account account = new Account("John Doe", new BigDecimal("1000.12346"));
        account.credit(new BigDecimal("100.00"));

        assertNotNull(account.getBalance());
        assertEquals(1100, account.getBalance().intValue());
        assertEquals("1100.12346", account.getBalance().toPlainString());
    }
}
