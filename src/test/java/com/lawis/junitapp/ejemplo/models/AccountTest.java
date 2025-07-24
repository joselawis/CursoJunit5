package com.lawis.junitapp.ejemplo.models;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.math.BigDecimal;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import com.lawis.junitapp.ejemplo.exceptions.NotEnoughMoneyException;

// @TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AccountTest {

    private Account account;

    @BeforeAll
    static void beforeAll() {
        System.out.println("Setting up before all tests");
    }

    @BeforeEach
    void setUp() {
        System.out.println("Setting up before each test");
        account = new Account("John Doe", new BigDecimal("1000.12346"));
    }

    @AfterEach
    void tearDown() {
        System.out.println("Cleaning up after each test");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("Cleaning up after all tests");
    }

    @Test
    void testPersonAccount() {
        String expected = "John Doe";
        String actual = account.getPerson();

        assertEquals(expected, actual);
        assertTrue(() -> actual.equals(expected));
    }

    @Test
    void testBalanceAccount() {
        assertEquals(1000.12346, account.getBalance().doubleValue());
        assertNotNull(account.getBalance());
        assertFalse(() -> account.getBalance().compareTo(BigDecimal.ZERO) < 0);
        assertTrue(() -> account.getBalance().compareTo(BigDecimal.ZERO) > 0);
    }

    @Test
    void testReferenceAccount() {
        Account account2 = new Account("John Doe", new BigDecimal("1000.12346"));

        assertEquals(account2, account);
    }

    @Test
    void testDebitAccount() {
        account.debit(new BigDecimal("100.00"));

        assertNotNull(account.getBalance());
        assertEquals(900, account.getBalance().intValue());
        assertEquals("900.12346", account.getBalance().toPlainString());
    }

    @Test
    void testDebitNotEnoughMoneyException() {
        BigDecimal amount = new BigDecimal("1100.00");
        Exception exception = assertThrows(NotEnoughMoneyException.class,
                () -> account.debit(amount));

        String actualMessage = exception.getMessage();
        String expectedMessage = "Not enough money in the account";
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testCreditAccount() {
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
        Account from = new Account("Alice", new BigDecimal("500.00"));
        Account to = new Account("Bob", new BigDecimal("300.00"));

        Bank bank = new Bank("My Bank");

        BigDecimal transferAmount = new BigDecimal("600.00");
        Exception exception = assertThrows(NotEnoughMoneyException.class,
                () -> bank.transfer(from, to, transferAmount));

        String actualMessage = exception.getMessage();
        String expectedMessage = "Not enough money in the account";
        assertEquals(expectedMessage, actualMessage);

        assertEquals(new BigDecimal("500.00"), from.getBalance());
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
