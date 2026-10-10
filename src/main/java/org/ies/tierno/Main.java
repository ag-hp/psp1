package org.ies.tierno;

import org.ies.tierno.components.ChargeTask;
import org.ies.tierno.components.ChargingStation;
import org.ies.tierno.components.StationStats;
import org.ies.tierno.model.StatsSnapshot;
import org.ies.tierno.model.Vehicle;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Parte 3: programa principal
// La clase Main debe:
public class Main {
    public static void main(String[] args) {
        StationStats stats = new StationStats();
        ChargingStation station = new ChargingStation(stats);
        Random random = new Random();

        int sumaKwh = 0;

        // Crear un ExecutorService con Executors.newFixedThreadPool(8) dentro de un try-with-resources.
        try (var es = Executors.newFixedThreadPool(8)) {
            for (int i = 0; i < 20; i++) {
                // Generar los 20 vehículos con matrículas y kWh aleatorios.
                String plate = "MAT-" + random.nextInt(1000);
                int kwh = 5 + random.nextInt(46);
                sumaKwh = sumaKwh + kwh;

                Vehicle vehicle = new Vehicle(plate, kwh);
                ChargeTask task = new ChargeTask(station, vehicle);

                // Enviar al pool una tarea Runnable por cada vehículo.
                es.submit(task);
            }
        }

        // Cuando el pool haya terminado, mostrar el snapshot() final y hacer dos comprobaciones de coherencia:
        StatsSnapshot snapshot = stats.snapshot();
        System.out.println("Snapshot final: " + snapshot);

        // La suma de kWh de los vehículos generados coincide con totalKwh.
        if (sumaKwh == snapshot.totalKwh()) {
            System.out.println("Comprobación 1 correcta");
        } else {
            System.out.println("Comprobación 1 INCORRECTA");
        }

        // totalCents == totalKwh * 45.
        if (snapshot.totalCents() == snapshot.totalKwh() * 45) {
            System.out.println("Comprobación 2 correcta");
        } else {
            System.out.println("Comprobación 2 INCORRECTA");
        }
    }
}