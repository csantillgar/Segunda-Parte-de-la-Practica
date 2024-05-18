package storage;

import model.BacteriaPopulation;
import model.Experiment;
import model.FoodPattern;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;

public class ExperimentLoader {

    public Experiment loadExperiment(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String name = reader.readLine();
        LocalDate startDate = LocalDate.parse(reader.readLine());
        LocalDate endDate = LocalDate.parse(reader.readLine());

        Experiment experiment = new Experiment(name, startDate, endDate);

        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            String populationName = parts[0];
            LocalDate populationStartDate = LocalDate.parse(parts[1]);
            int initialBacteriaCount = Integer.parseInt(parts[2]);
            FoodPattern foodPattern = FoodPattern.valueOf(parts[3]);
            BacteriaPopulation population = new BacteriaPopulation(populationName, populationStartDate, initialBacteriaCount, foodPattern);
            experiment.addPopulation(population);
        }

        reader.close();
        return experiment;
    }
}
