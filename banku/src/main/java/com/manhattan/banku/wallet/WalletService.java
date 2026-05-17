package com.manhattan.banku.wallet;

/**
 * Phase 0 wallet use-cases.
 * TODO: implement these operations against an in-memory map first.
 * TODO: keep persistence out of phase 0.
 */
public interface WalletService {

    // TODO: create a wallet with zero balance and reject duplicate ids.
    Wallet createWallet(String walletId);

    // TODO: return the wallet or fail clearly if it does not exist.
    Wallet getWallet(String walletId);

    // TODO: validate the amount and add funds.
    Wallet deposit(String walletId, long amountInCents);

    // TODO: validate the amount and prevent overdrafts.
    Wallet withdraw(String walletId, long amountInCents);

    // TODO: move funds atomically between two wallets.
    void transfer(String fromWalletId, String toWalletId, long amountInCents);
}
