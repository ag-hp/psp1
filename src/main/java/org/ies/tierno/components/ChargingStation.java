package org.ies.tierno.components;

import org.ies.tierno.model.ChargeResult;
import org.ies.tierno.model.Vehicle;

import java.util.concurrent.Semaphore;

// Parte 1: acceso a los cargadores
// Implementa la clase ChargingStation, que gestiona el uso de los cargadores con estas reglas:
public class ChargingStation {
    // Nunca puede haber más de 4 vehículos cargando a la vez.
    private final Semaphore chargers = new Semaphore(4);
    private final StationStats stats;

    public ChargingStation(StationStats stats) {
        this.stats = stats;
    }

    // La clase expone el metodo ChargeResult charge(Vehicle vehicle), que:
    public ChargeResult charge(Vehicle vehicle) {
        try {
            // Un vehículo que encuentra todos los cargadores ocupados espera, sin consumir CPU, hasta que uno quede libre.
            // Espera a tener un cargador disponible.
            chargers.acquire();
            try {
                // Muestra por consola que el vehículo empieza a cargar.
                System.out.println(vehicle.plate() + " vehículo empieza a cargar.");
                // Simula la carga con Thread.sleep(vehicle.kwh() * 20L).
                Thread.sleep(vehicle.kwh() * 20L);
                // Anota la carga en el registro de la jornada.
                stats.register(vehicle.kwh());
                // Muestra por consola que el vehículo termina y libera el cargador.
                System.out.println(vehicle.plate() + " vehículo termina y libera el cargador.");
            } finally {
                chargers.release();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return new ChargeResult(vehicle.plate(), vehicle.kwh(), vehicle.kwh() * 45L);
    }
}