package storage;

import model.BacteriaPopulation;
import model.Experiment;
import model.FoodPattern;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.time.LocalDate;

public class ExperimentLoader {

    public Experiment loadExperiment(Reader reader) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(reader);

        // Leer y validar el nombre del experimento
        String name = bufferedReader.readLine();
        if (name == null || name.isEmpty()) {
            throw new IOException("El nombre del experimento no puede estar vacío.");
        }

        // Leer y validar la fecha de inicio
        String startDateStr = bufferedReader.readLine();
        if (startDateStr == null || startDateStr.isEmpty()) {
            throw new IOException("La fecha de inicio del experimento no puede estar vacía.");
        }
        LocalDate startDate = LocalDate.parse(startDateStr);

        // Leer y validar la fecha de finalización
        String endDateStr = bufferedReader.readLine();
        if (endDateStr == null || endDateStr.isEmpty()) {
            throw new IOException("La fecha de finalización del experimento no puede estar vacía.");
        }
        LocalDate endDate = LocalDate.parse(endDateStr);

        Experiment experiment = new Experiment(name, startDate, endDate);

        String line;
        while ((line = bufferedReader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length < 4) {
                throw new IOException("La línea del archivo no contiene suficientes datos: " + line);
            }

            String populationName = parts[0];
            LocalDate populationStartDate = LocalDate.parse(parts[1]);
            int initialBacteriaCount = Integer.parseInt(parts[2]);
            FoodPattern foodPattern;

            System.out.println("Parsing FoodPattern: " + parts[3]); // Línea de depuración

            try {
                foodPattern = FoodPattern.valueOf(parts[3].trim()); // Usar trim() para eliminar espacios adicionales
            } catch (IllegalArgumentException e) {
                throw new IOException("Valor de FoodPattern inválido: " + parts[3]);
            }

            BacteriaPopulation population = new BacteriaPopulation(populationName, populationStartDate, initialBacteriaCount, foodPattern);
            experiment.addPopulation(population);
        }

        bufferedReader.close();
        return experiment;
    }

    // Método original para cargar desde una ruta de archivo
    public Experiment loadExperiment(String filePath) throws IOException {
        try (Reader reader = new FileReader(filePath)) {
            return loadExperiment(reader);
        }
    }
}




