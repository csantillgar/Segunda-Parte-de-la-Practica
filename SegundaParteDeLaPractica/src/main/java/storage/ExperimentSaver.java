package storage;

import model.BacteriaPopulation;
import model.Experiment;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ExperimentSaver {

    public void saveExperiment(Experiment experiment, String filePath) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
        writer.write(experiment.getName());
        writer.newLine();
        writer.write(experiment.getStartDate().toString());
        writer.newLine();
        writer.write(experiment.getEndDate().toString());
        writer.newLine();

        for (BacteriaPopulation population : experiment.getPopulations()) {
            writer.write(population.getName() + ",");
            writer.write(population.getStartDate().toString() + ",");
            writer.write(population.getInitialBacteriaCount() + ",");
            writer.write(population.getFoodPattern().name());
            writer.newLine();
        }

        writer.close();
    }
}
