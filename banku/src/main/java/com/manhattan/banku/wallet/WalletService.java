package com.manhattan.banku.wallet;

/**
 * Phase 01 wallet use-cases.
 */
public interface WalletService {

    Wallet createWallet(String walletId);

    Wallet getWallet(String walletId);

    Wallet deposit(String walletId, long amountInCents);

    Wallet withdraw(String walletId, long amountInCents);

    void transfer(String fromWalletId, String toWalletId, long amountInCents);
}
