package com.manhattan.banku.wallet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WalletServiceTest {

    private InMemoryWalletService service;

    @BeforeEach
    void setUp() {
        service = new InMemoryWalletService();
    }

    @Test
    void createWalletStartsAtZeroAndRejectsDuplicates() {
        Wallet createdWallet = service.createWallet("wallet-1");

        assertEquals("wallet-1", createdWallet.id());
        assertEquals(0L, createdWallet.balanceInCents());
        assertEquals(createdWallet, service.getWallet("wallet-1"));

        assertThrows(IllegalArgumentException.class, () -> service.createWallet("wallet-1"));
        assertThrows(IllegalArgumentException.class, () -> service.createWallet("   "));
    }

    @Test
    void getWalletRejectsMissingWallets() {
        assertThrows(IllegalArgumentException.class, () -> service.getWallet("missing-wallet"));
        assertThrows(IllegalArgumentException.class, () -> service.getWallet(""));
    }

    @Test
    void depositIncreasesBalanceAndRejectsInvalidAmounts() {
        service.createWallet("wallet-1");

        Wallet depositedWallet = service.deposit("wallet-1", 250L);

        assertEquals(250L, depositedWallet.balanceInCents());
        assertEquals(250L, service.getWallet("wallet-1").balanceInCents());
        assertThrows(IllegalArgumentException.class, () -> service.deposit("wallet-1", 0L));
        assertThrows(IllegalArgumentException.class, () -> service.deposit("wallet-1", -1L));
    }

    @Test
    void withdrawDecreasesBalanceAndRejectsOverdrafts() {
        service.createWallet("wallet-1");
        service.deposit("wallet-1", 500L);

        Wallet withdrawnWallet = service.withdraw("wallet-1", 200L);

        assertEquals(300L, withdrawnWallet.balanceInCents());
        assertEquals(300L, service.getWallet("wallet-1").balanceInCents());
        assertThrows(IllegalArgumentException.class, () -> service.withdraw("wallet-1", 0L));
        assertThrows(IllegalArgumentException.class, () -> service.withdraw("wallet-1", 400L));
    }

    @Test
    void transferMovesFundsAtomicallyAndRejectsFailures() {
        service.createWallet("from-wallet");
        service.createWallet("to-wallet");
        service.deposit("from-wallet", 500L);
        service.deposit("to-wallet", 100L);

        service.transfer("from-wallet", "to-wallet", 200L);

        assertEquals(300L, service.getWallet("from-wallet").balanceInCents());
        assertEquals(300L, service.getWallet("to-wallet").balanceInCents());

        assertThrows(IllegalArgumentException.class, () -> service.transfer("from-wallet", "from-wallet", 100L));
        assertThrows(IllegalArgumentException.class, () -> service.transfer("from-wallet", "to-wallet", 1000L));

        assertEquals(300L, service.getWallet("from-wallet").balanceInCents());
        assertEquals(300L, service.getWallet("to-wallet").balanceInCents());
    }
}
