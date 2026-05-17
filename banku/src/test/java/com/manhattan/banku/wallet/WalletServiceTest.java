package com.manhattan.banku.wallet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class WalletServiceTest {

    private InMemoryWalletService service;

    @BeforeEach
    void setUp() {
        service = new InMemoryWalletService();
    }

    @Test
    void createWalletStartsAtZeroAndRejectsDuplicatesAndBlankIds() {
        Wallet createdWallet = service.createWallet("wallet-1");

        assertEquals(new Wallet("wallet-1", 0L), createdWallet);
        assertWallet("wallet-1", 0L);

        assertIllegalArgument("Wallet already exists", () -> service.createWallet("wallet-1"));
        assertWallet("wallet-1", 0L);

        assertIllegalArgument("Wallet ID cannot be blank", () -> service.createWallet("   "));
        assertWallet("wallet-1", 0L);
    }

    @Test
    void getWalletReturnsStoredWalletAndRejectsMissingOrBlankIds() {
        service.createWallet("wallet-1");
        service.deposit("wallet-1", 250L);

        assertEquals(new Wallet("wallet-1", 250L), service.getWallet("wallet-1"));
        assertIllegalArgument("Wallet does not exist", () -> service.getWallet("missing-wallet"));
        assertIllegalArgument("Wallet ID cannot be blank", () -> service.getWallet(""));
        assertWallet("wallet-1", 250L);
    }

    @Test
    void depositIncreasesBalanceAndRejectsInvalidRequestsWithoutMutatingBalance() {
        service.createWallet("wallet-1");

        Wallet depositedWallet = service.deposit("wallet-1", 250L);

        assertEquals(new Wallet("wallet-1", 250L), depositedWallet);
        assertWallet("wallet-1", 250L);

        assertIllegalArgument("Amount in cents must be positive", () -> service.deposit("wallet-1", 0L));
        assertWallet("wallet-1", 250L);

        assertIllegalArgument("Amount in cents must be positive", () -> service.deposit("wallet-1", -1L));
        assertWallet("wallet-1", 250L);

        assertIllegalArgument("Wallet ID cannot be blank", () -> service.deposit("   ", 50L));
        assertWallet("wallet-1", 250L);

        assertIllegalArgument("Wallet does not exist", () -> service.deposit("missing-wallet", 50L));
        assertWallet("wallet-1", 250L);
    }

    @Test
    void withdrawDecreasesBalanceAndRejectsInvalidRequestsWithoutMutatingBalance() {
        service.createWallet("wallet-1");
        service.deposit("wallet-1", 500L);

        Wallet withdrawnWallet = service.withdraw("wallet-1", 200L);

        assertEquals(new Wallet("wallet-1", 300L), withdrawnWallet);
        assertWallet("wallet-1", 300L);

        assertIllegalArgument("Amount in cents must be positive", () -> service.withdraw("wallet-1", 0L));
        assertWallet("wallet-1", 300L);

        assertIllegalArgument("Amount in cents must be positive", () -> service.withdraw("wallet-1", -1L));
        assertWallet("wallet-1", 300L);

        assertIllegalArgument("Wallet ID cannot be blank", () -> service.withdraw("   ", 100L));
        assertWallet("wallet-1", 300L);

        assertIllegalArgument("Wallet does not exist", () -> service.withdraw("missing-wallet", 100L));
        assertWallet("wallet-1", 300L);

        assertIllegalArgument("Insufficient funds", () -> service.withdraw("wallet-1", 400L));
        assertWallet("wallet-1", 300L);
    }

    @Test
    void balanceOverflowIsRejectedWithoutMutatingState() {
        service.createWallet("wallet-1");
        service.deposit("wallet-1", Long.MAX_VALUE);

        assertWallet("wallet-1", Long.MAX_VALUE);
        assertIllegalArgument("Wallet balance overflow", () -> service.deposit("wallet-1", 1L));
        assertWallet("wallet-1", Long.MAX_VALUE);

        service.createWallet("source-wallet");
        service.createWallet("target-wallet");
        service.deposit("source-wallet", 1L);
        service.deposit("target-wallet", Long.MAX_VALUE);

        assertIllegalArgument("Wallet balance overflow",
                () -> service.transfer("source-wallet", "target-wallet", 1L));
        assertWallet("source-wallet", 1L);
        assertWallet("target-wallet", Long.MAX_VALUE);
    }

    @Test
    void transferMovesFundsAtomicallyAndRejectsInvalidRequestsWithoutMutatingBalances() {
        service.createWallet("from-wallet");
        service.createWallet("to-wallet");
        service.deposit("from-wallet", 500L);
        service.deposit("to-wallet", 100L);

        service.transfer("from-wallet", "to-wallet", 200L);

        assertWallet("from-wallet", 300L);
        assertWallet("to-wallet", 300L);

        assertIllegalArgument("Amount in cents must be positive", () -> service.transfer("from-wallet", "to-wallet", 0L));
        assertWallet("from-wallet", 300L);
        assertWallet("to-wallet", 300L);

        assertIllegalArgument("Amount in cents must be positive", () -> service.transfer("from-wallet", "to-wallet", -1L));
        assertWallet("from-wallet", 300L);
        assertWallet("to-wallet", 300L);

        assertIllegalArgument("Source and destination wallets must be different",
                () -> service.transfer("from-wallet", "from-wallet", 100L));
        assertWallet("from-wallet", 300L);
        assertWallet("to-wallet", 300L);

        assertIllegalArgument("Wallet ID cannot be blank", () -> service.transfer("   ", "to-wallet", 100L));
        assertWallet("from-wallet", 300L);
        assertWallet("to-wallet", 300L);

        assertIllegalArgument("Wallet ID cannot be blank", () -> service.transfer("from-wallet", "   ", 100L));
        assertWallet("from-wallet", 300L);
        assertWallet("to-wallet", 300L);

        assertIllegalArgument("Wallet does not exist", () -> service.transfer("missing-wallet", "to-wallet", 100L));
        assertWallet("from-wallet", 300L);
        assertWallet("to-wallet", 300L);

        assertIllegalArgument("Wallet does not exist", () -> service.transfer("from-wallet", "missing-wallet", 100L));
        assertWallet("from-wallet", 300L);
        assertWallet("to-wallet", 300L);

        assertIllegalArgument("Insufficient funds", () -> service.transfer("from-wallet", "to-wallet", 500L));
        assertWallet("from-wallet", 300L);
        assertWallet("to-wallet", 300L);
    }

    private void assertWallet(String walletId, long balanceInCents) {
        assertEquals(new Wallet(walletId, balanceInCents), service.getWallet(walletId));
    }

    private static void assertIllegalArgument(String expectedMessage, Executable executable) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, executable);
        assertEquals(expectedMessage, exception.getMessage());
    }
}
