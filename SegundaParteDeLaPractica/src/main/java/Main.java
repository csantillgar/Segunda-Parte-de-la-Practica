import model.Experiment;
import model.MonteCarloSimulation;
import storage.ExperimentLoader;
import InterfazUsuario.MainFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            ExperimentLoader loader = new ExperimentLoader();
            Experiment experiment = loader.loadExperiment("src/main/resources/experiments/example_experiment.txt");

            MonteCarloSimulation simulation = new MonteCarloSimulation();
            for (var population : experiment.getPopulations()) {
                simulation.initialize(population);
                simulation.runSimulation(30, population.getFoodPattern());
            }

            SwingUtilities.invokeLater(() -> {
                MainFrame mainFrame = new MainFrame();
                mainFrame.setVisible(true);
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
