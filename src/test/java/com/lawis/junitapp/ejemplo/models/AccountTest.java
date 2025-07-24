package com.lawis.junitapp.ejemplo.models;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.math.BigDecimal;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
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

        BigDecimal amount = new BigDecimal("1100.00");
        Exception exception = assertThrows(NotEnoughMoneyException.class,
                () -> account.debit(amount));

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

    @Test
    void testTransferMoneyAccounts() {
        Account from = new Account("Alice", new BigDecimal("500.00"));
        Account to = new Account("Bob", new BigDecimal("300.00"));

        Bank bank = new Bank("My Bank");
        bank.transfer(from, to, new BigDecimal("200.00"));

        assertEquals(new BigDecimal("300.00"), from.getBalance());
        assertEquals(new BigDecimal("500.00"), to.getBalance());
    }

    @Test
    void testTransferNotEnoughMoneyException() {
        Account from = new Account("Alice", new BigDecimal("100.00"));
        Account to = new Account("Bob", new BigDecimal("300.00"));

        Bank bank = new Bank("My Bank");

        BigDecimal transferAmount = new BigDecimal("200.00");
        Exception exception = assertThrows(NotEnoughMoneyException.class,
                () -> bank.transfer(from, to, transferAmount));

        String actualMessage = exception.getMessage();
        String expectedMessage = "Not enough money in the account";
        assertEquals(expectedMessage, actualMessage);

        assertEquals(new BigDecimal("100.00"), from.getBalance());
        assertEquals(new BigDecimal("300.00"), to.getBalance());

    }

    @Test
    @Disabled("This test is disabled for demonstration purposes")
    void testDisabled() {
        fail("This test is disabled and should not run");
    }

    @Test
    @DisplayName("Test relation between Bank and Account")
    void testRelationBankAccount() {
        Account from = new Account("Alice", new BigDecimal("500.00"));
        Account to = new Account("Bob", new BigDecimal("300.00"));

        Bank bank = new Bank("My Bank");
        bank.addAccount(from);
        bank.addAccount(to);
        assertAll(
                () -> assertEquals(2, bank.getAccounts().size()),
                () -> assertTrue(bank.getAccounts().contains(from)),
                () -> assertTrue(bank.getAccounts().contains(to)),
                () -> assertEquals(bank, from.getBank()),
                () -> assertEquals("Alice",
                        bank.getAccounts().stream()
                                .filter(a -> a.getPerson().equals("Alice"))
                                .findFirst().get().getPerson()),
                () -> assertEquals("Bob",
                        bank.getAccounts().stream()
                                .filter(a -> a.getPerson().equals("Bob"))
                                .findFirst().get().getPerson()),

                () -> assertTrue(bank.getAccounts().stream()
                        .filter(a -> a.getPerson().equals("Alice"))
                        .findFirst().isPresent()),
                () -> assertTrue(bank.getAccounts().stream()
                        .filter(a -> a.getPerson().equals("Bob"))
                        .findFirst().isPresent(), () -> "Bob account should be present in the bank"),

                () -> assertTrue(bank.getAccounts().stream()
                        .anyMatch(a -> "Alice".equals(a.getPerson()))),
                () -> assertTrue(bank.getAccounts().stream()
                        .anyMatch(a -> "Bob".equals(a.getPerson()))));
    }
}
