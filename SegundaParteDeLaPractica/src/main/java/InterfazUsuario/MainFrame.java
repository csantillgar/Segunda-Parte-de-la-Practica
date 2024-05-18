package InterfazUsuario;

import javax.swing.*;
import java.awt.*;
import model.Experiment;
import model.BacteriaPopulation;
import java.util.List;

public class MainFrame extends JFrame {
    private JPanel experimentPanel;
    private JPanel populationPanel;

    public MainFrame(Experiment experiment) {
        setTitle("Simulación de Crecimiento Bacteriano");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(1, 2));

        experimentPanel = new JPanel();
        populationPanel = new JPanel();

        experimentPanel.setLayout(new BorderLayout());
        populationPanel.setLayout(new BorderLayout());

        JLabel experimentLabel = new JLabel("Panel de Experimentos");
        experimentPanel.add(experimentLabel, BorderLayout.NORTH);

        JLabel populationLabel = new JLabel("Panel de Poblaciones");
        populationPanel.add(populationLabel, BorderLayout.NORTH);

        // Mostrar información del experimento
        JTextArea experimentInfo = new JTextArea();
        experimentInfo.setText("Nombre del Experimento: " + experiment.getName() + "\n" +
                "Fecha de Inicio: " + experiment.getStartDate() + "\n" +
                "Fecha de Fin: " + experiment.getEndDate());
        experimentInfo.setEditable(false);
        experimentPanel.add(new JScrollPane(experimentInfo), BorderLayout.CENTER);

        // Mostrar lista de poblaciones
        DefaultListModel<String> populationListModel = new DefaultListModel<>();
        for (BacteriaPopulation population : experiment.getPopulations()) {
            populationListModel.addElement(population.getName() + " - " + population.getFoodPattern());
        }
        JList<String> populationList = new JList<>(populationListModel);
        populationPanel.add(new JScrollPane(populationList), BorderLayout.CENTER);

        add(experimentPanel);
        add(populationPanel);
    }
}

