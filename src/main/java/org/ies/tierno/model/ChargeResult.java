package org.ies.tierno.model;

// Vehicle y ChargeResult deben ser record:
// public record ChargeResult(String plate, int kwh, long cents) {}
public record ChargeResult(String plate, int kwh, long cents) {
}
