package InterfazUsuario;
import model.Experiment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExperimentGUI extends JFrame {

    private Experiment experiment;

    public ExperimentGUI(Experiment experiment) {
        this.experiment = experiment;

        setTitle("Gestión de Experimentos de Bacterias");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 400);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));

        JButton sortByStartDateButton = new JButton("Ordenar por Fecha de Inicio");
        sortByStartDateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                experiment.sortByStartDate();
                // Actualizar la GUI con las poblaciones ordenadas
            }
        });

        JButton sortByNameButton = new JButton("Ordenar por Nombre");
        sortByNameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                experiment.sortByName();
                // Actualizar la GUI con las poblaciones ordenadas
            }
        });

        JButton sortByBacteriaCountButton = new JButton("Ordenar por Número de Bacterias");
        sortByBacteriaCountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                experiment.sortByBacteriaCount();
                // Actualizar la GUI con las poblaciones ordenadas
            }
        });

        panel.add(sortByStartDateButton);
        panel.add(sortByNameButton);
        panel.add(sortByBacteriaCountButton);

        add(panel);
        setVisible(true);
    }

    // Otros métodos para manipular la interfaz de usuario
}
