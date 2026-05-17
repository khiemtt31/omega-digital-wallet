package com.manhattan.banku.wallet;

/**
 * Phase 0 wallet model.
 * TODO: keep money in minor units (cents) so you can avoid decimal math.
 * TODO: add more fields only when the phase-0 scope really needs them.
 */
public record Wallet(String id, long balanceInCents) {
    // TODO: add validation rules here later if you want the model to reject bad data.
}
