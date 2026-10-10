package org.ies.tierno.components;

import org.ies.tierno.model.StatsSnapshot;

// Parte 2: registro de la jornada
public class StationStats {

    // Implementa la clase StationStats, que guarda dos datos:
    // totalKwh -> energía total suministrada
    private long totalKwh = 0;
    // totalCents -> ingresos totales, en céntimos
    private long totalCents = 0;

    // Métodos obligatorios: void register(int kwh): suma la energía de una carga y su importe.
    // Los valores del registro tienen que ser siempre correctos, sea cual sea el número de vehículos y el orden en que se ejecuten los hilos.
    public synchronized void register(int kwh) {
        totalKwh += kwh;
        totalCents += kwh * 45L;
    }

    // Métodos obligatorios: StatsSnapshot snapshot(): devuelve un record con los dos valores.
    //  Además, snapshot() nunca puede devolver una pareja de valores incoherente entre sí.
    public synchronized StatsSnapshot snapshot() {
        return new StatsSnapshot(totalKwh, totalCents);
    }
}