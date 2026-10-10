package org.ies.tierno.components;

import org.ies.tierno.model.Vehicle;

public class ChargeTask implements Runnable {
    private final ChargingStation station;
    private final Vehicle vehicle;

    public ChargeTask(ChargingStation station, Vehicle vehicle) {
        this.station = station;
        this.vehicle = vehicle;
    }

    @Override
    public void run() {
        station.charge(vehicle);
    }
}