package com.manhattan.banku.wallet;

import java.util.HashMap;
import java.util.Map;

/**
 * Phase 01 in-memory wallet implementation.
 */
public class InMemoryWalletService implements WalletService {

    private static final String WALLET_ALREADY_EXISTS_MESSAGE = "Wallet already exists";
    private static final String WALLET_DOES_NOT_EXIST_MESSAGE = "Wallet does not exist";
    private static final String WALLET_ID_CANNOT_BE_BLANK_MESSAGE = "Wallet ID cannot be blank";
    private static final String AMOUNT_MUST_BE_POSITIVE_MESSAGE = "Amount in cents must be positive";
    private static final String WALLET_BALANCE_OVERFLOW_MESSAGE = "Wallet balance overflow";
    private static final String INSUFFICIENT_FUNDS_MESSAGE = "Insufficient funds";
    private static final String DIFFERENT_WALLETS_REQUIRED_MESSAGE = "Source and destination wallets must be different";

    private final Map<String, Wallet> wallets = new HashMap<>();
    private final Object lock = new Object();

    @Override
    public Wallet createWallet(String walletId) {
        String normalizedWalletId = requireWalletId(walletId);

        synchronized (lock) {
            if (wallets.containsKey(normalizedWalletId)) {
                throw new IllegalArgumentException(WALLET_ALREADY_EXISTS_MESSAGE);
            }

            Wallet wallet = new Wallet(normalizedWalletId, 0L);
            wallets.put(normalizedWalletId, wallet);
            return wallet;
        }
    }

    @Override
    public Wallet getWallet(String walletId) {
        String normalizedWalletId = requireWalletId(walletId);
        synchronized (lock) {
            Wallet wallet = wallets.get(normalizedWalletId);

            if (wallet == null) {
                throw new IllegalArgumentException(WALLET_DOES_NOT_EXIST_MESSAGE);
            }

            return wallet;
        }
    }

    @Override
    public Wallet deposit(String walletId, long amountInCents) {
        String normalizedWalletId = requireWalletId(walletId);
        requirePositiveAmount(amountInCents);

        synchronized (lock) {
            Wallet currentWallet = requireExistingWallet(normalizedWalletId);
            Wallet updatedWallet = new Wallet(normalizedWalletId,
                    addBalance(currentWallet.balanceInCents(), amountInCents));
            wallets.put(normalizedWalletId, updatedWallet);
            return updatedWallet;
        }
    }

    @Override
    public Wallet withdraw(String walletId, long amountInCents) {
        String normalizedWalletId = requireWalletId(walletId);
        requirePositiveAmount(amountInCents);

        synchronized (lock) {
            Wallet currentWallet = requireExistingWallet(normalizedWalletId);

            if (currentWallet.balanceInCents() < amountInCents) {
                throw new IllegalArgumentException(INSUFFICIENT_FUNDS_MESSAGE);
            }

            Wallet updatedWallet = new Wallet(normalizedWalletId,
                    Math.subtractExact(currentWallet.balanceInCents(), amountInCents));
            wallets.put(normalizedWalletId, updatedWallet);
            return updatedWallet;
        }
    }

    @Override
    public void transfer(String fromWalletId, String toWalletId, long amountInCents) {
        String normalizedFromWalletId = requireWalletId(fromWalletId);
        String normalizedToWalletId = requireWalletId(toWalletId);
        requirePositiveAmount(amountInCents);

        if (normalizedFromWalletId.equals(normalizedToWalletId)) {
            throw new IllegalArgumentException(DIFFERENT_WALLETS_REQUIRED_MESSAGE);
        }

        synchronized (lock) {
            Wallet fromWallet = requireExistingWallet(normalizedFromWalletId);
            Wallet toWallet = requireExistingWallet(normalizedToWalletId);

            if (fromWallet.balanceInCents() < amountInCents) {
                throw new IllegalArgumentException(INSUFFICIENT_FUNDS_MESSAGE);
            }

            Wallet updatedFromWallet = new Wallet(normalizedFromWalletId,
                    Math.subtractExact(fromWallet.balanceInCents(), amountInCents));
            Wallet updatedToWallet = new Wallet(normalizedToWalletId,
                    addBalance(toWallet.balanceInCents(), amountInCents));

            wallets.put(normalizedFromWalletId, updatedFromWallet);
            wallets.put(normalizedToWalletId, updatedToWallet);
        }
    }

    private static String requireWalletId(String walletId) {
        if (walletId == null || walletId.isBlank()) {
            throw new IllegalArgumentException(WALLET_ID_CANNOT_BE_BLANK_MESSAGE);
        }

        return walletId;
    }

    private static void requirePositiveAmount(long amountInCents) {
        if (amountInCents <= 0L) {
            throw new IllegalArgumentException(AMOUNT_MUST_BE_POSITIVE_MESSAGE);
        }
    }

    private static long addBalance(long balanceInCents, long amountInCents) {
        try {
            return Math.addExact(balanceInCents, amountInCents);
        } catch (ArithmeticException exception) {
            throw new IllegalArgumentException(WALLET_BALANCE_OVERFLOW_MESSAGE, exception);
        }
    }

    private Wallet requireExistingWallet(String walletId) {
        Wallet wallet = wallets.get(walletId);

        if (wallet == null) {
            throw new IllegalArgumentException(WALLET_DOES_NOT_EXIST_MESSAGE);
        }

        return wallet;
    }
}
