import model.Experiment;
import model.MonteCarloSimulation;
import storage.ExperimentLoader;
import InterfazUsuario.MainFrame;

import javax.swing.*;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) {
        try {
            // Cargar el experimento desde el archivo
            ExperimentLoader loader = new ExperimentLoader();
            InputStream inputStream = Main.class.getResourceAsStream("/experiments/example_experiment.txt");

            if (inputStream == null) {
                System.err.println("Resource not found: /experiments/example_experiment.txt");
                return;
            }

            Experiment experiment = loader.loadExperiment(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

            // Iniciar la simulación
            MonteCarloSimulation simulation = new MonteCarloSimulation();
            for (var population : experiment.getPopulations()) {
                simulation.initialize(population);
                simulation.runSimulation(30, population.getFoodPattern());
            }

            // Crear e iniciar la interfaz gráfica en el hilo de Swing
            SwingUtilities.invokeLater(() -> {
                MainFrame mainFrame = new MainFrame(experiment);
                mainFrame.setVisible(true);
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}








