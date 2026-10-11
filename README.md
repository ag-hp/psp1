<p align="center">
    <img src="https://img.shields.io/badge/Java_21-ED8B00?style=flat&logo=openjdk&logoColor=white" alt="Java 21">
    <img src="https://img.shields.io/badge/Maven-C71A36?style=flat&logo=apachemaven&logoColor=white" alt="Maven">
    <img src="https://img.shields.io/badge/IntelliJ_IDEA-000000?style=flat&logo=intellijidea&logoColor=white" alt="IntelliJ IDEA">
    <img src="https://img.shields.io/badge/Git-F05032?style=flat&logo=git&logoColor=white" alt="Git">
    <img src="https://img.shields.io/badge/GitHub-181717?style=flat&logo=github&logoColor=white" alt="GitHub">
</p>

<p align="center">
    <img src="https://img.shields.io/badge/PSP_%C2%B7_2%C2%BA_DAM-Programaci%C3%B3n_de_Servicios_y_Procesos-ED8B00?style=flat" alt="PSP 2º DAM">
    <img src="https://img.shields.io/badge/Semaphore-ED8B00?style=flat" alt="Semaphore">
    <img src="https://img.shields.io/badge/synchronized-ED8B00?style=flat" alt="synchronized">
    <img src="https://img.shields.io/badge/ExecutorService-ED8B00?style=flat" alt="ExecutorService">
    <img src="https://img.shields.io/badge/Records-ED8B00?style=flat" alt="Records">
</p>

---

# Tarea programación concurrente

Crea un proyecto llamado psp1

# <span style="color: #ffffff;">Parte 1: acceso a los cargadores</span>

Implementa la clase <code style="color: #ff79c6; background-color: #282a36; padding: 2px 5px; border-radius: 3px;">ChargingStation</code>, que gestiona el uso de los cargadores con estas reglas:

* Nunca puede haber más de 4 vehículos cargando a la vez.
* Un vehículo que encuentra todos los cargadores ocupados espera, sin consumir CPU, hasta que uno quede libre.

La clase expone el método <code style="color: #ff79c6; background-color: #282a36; padding: 2px 5px; border-radius: 3px;">ChargeResult charge(Vehicle vehicle)</code>, que:

1. Espera a tener un cargador disponible.
2. Muestra por consola que el vehículo empieza a cargar.
3. Simula la carga con <code style="color: #ff79c6; background-color: #282a36; padding: 2px 5px; border-radius: 3px;">Thread.sleep(vehicle.kwh() * 20L)</code>.
4. Anota la carga en el registro de la jornada.
5. Muestra por consola que el vehículo termina y libera el cargador.

<code style="color: #ff79c6; background-color: #282a36; padding: 2px 5px; border-radius: 3px;">Vehicle</code> y <code style="color: #ff79c6; background-color: #282a36; padding: 2px 5px; border-radius: 3px;">ChargeResult</code> deben ser <code style="color: #ff79c6; background-color: #282a36; padding: 2px 5px; border-radius: 3px;">record</code>:

<pre style="background-color: #0d1117; color: #ff79c6; padding: 10px; border-radius: 5px; font-family: monospace;">
public record Vehicle(String plate, int kwh) {}
public record ChargeResult(String plate, int kwh, long cents) {}
</pre>

# <span style="color: #ffffff;">Parte 2: registro de la jornada</span>

Implementa la clase <code style="color: #ff79c6; background-color: #282a36; padding: 2px 5px; border-radius: 3px;">StationStats</code>, que guarda dos datos:

| Campo | Significado |
| :--- | :--- |
| <code style="color: #ff79c6; background-color: #282a36; padding: 2px 5px; border-radius: 3px;">totalkWh</code> | energía total suministrada |
| <code style="color: #ff79c6; background-color: #282a36; padding: 2px 5px; border-radius: 3px;">totalCents</code> | ingresos totales, en céntimos |

Métodos obligatorios:

* <code style="color: #ff79c6; background-color: #282a36; padding: 2px 5px; border-radius: 3px;">void register(int kwh)</code>: suma la energía de una carga y su importe.
* <code style="color: #ff79c6; background-color: #282a36; padding: 2px 5px; border-radius: 3px;">StatsSnapshot snapshot()</code>: devuelve un <code style="color: #ff79c6; background-color: #282a36; padding: 2px 5px; border-radius: 3px;">record</code> con los dos valores.

Los valores del registro tienen que ser siempre correctos, sea cual sea el número de vehículos y el orden en que se ejecuten los hilos. Además, <code style="color: #ff79c6; background-color: #282a36; padding: 2px 5px; border-radius: 3px;">snapshot()</code> nunca puede devolver una pareja de valores incoherente entre sí.

# <span style="color: #ffffff;">Parte 3: programa principal</span>

La clase <code style="color: #ff79c6; background-color: #282a36; padding: 2px 5px; border-radius: 3px;">Main</code> debe:

* Generar los 20 vehículos con matrículas y kWh aleatorios.
* Crear un <code style="color: #ff79c6; background-color: #282a36; padding: 2px 5px; border-radius: 3px;">ExecutorService</code> con <code style="color: #ff79c6; background-color: #282a36; padding: 2px 5px; border-radius: 3px;">Executors.newFixedThreadPool(8)</code> dentro de un <code style="color: #ff79c6; background-color: #282a36; padding: 2px 5px; border-radius: 3px;">try-with-resources</code>.
* Enviar al pool una tarea <code style="color: #ff79c6; background-color: #282a36; padding: 2px 5px; border-radius: 3px;">Runnable</code> por cada vehículo.
* Cuando el pool haya terminado, mostrar el <code style="color: #ff79c6; background-color: #282a36; padding: 2px 5px; border-radius: 3px;">snapshot()</code> final y hacer dos **comprobaciones de coherencia**:
  * La suma de kWh de los vehículos generados coincide con <code style="color: #ff79c6; background-color: #282a36; padding: 2px 5px; border-radius: 3px;">totalkWh</code>.
  * **<code style="color: #ff79c6; background-color: #282a36; padding: 2px 5px; border-radius: 3px;">totalCents == totalkWh * 45</code>**.

# <span style="color: #ffffff;">Restricciones</span>

* Java 21, sin crear <code style="color: #ff79c6; background-color: #282a36; padding: 2px 5px; border-radius: 3px;">Thread</code> directamente.
* Solo puedes usar los mecanismos de sincronización vistos en la unidad. No se permiten clases del paquete <code style="color: #ff79c6; background-color: #282a36; padding: 2px 5px; border-radius: 3px;">java.util.concurrent.atomic</code>, implementaciones de <code style="color: #ff79c6; background-color: #282a36; padding: 2px 5px; border-radius: 3px;">Lock</code>, colecciones concurrentes ni <code style="color: #ff79c6; background-color: #282a36; padding: 2px 5px; border-radius: 3px;">wait()/notify()</code>.
* La Parte 1 y la Parte 2 deben resolverse con **mecanismos de sincronización distintos**.

---

<p align="center">
  <a href="https://github.com/ag-hp/dam.git"><img src="https://img.shields.io/badge/VER_REPOSITORIO_COMPLETO-DESARROLLO_DE_APLICACIONES_MULTIPLATAFORMA-238636?style=for-the-badge&logo=github&logoColor=white&labelColor=000000" alt="Ver Repositorio Completo Desarrollo de Aplicaciones Multiplataforma"></a>
</p>
