package InterfazUsuario;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("Simulación de Crecimiento Bacteriano");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Aquí añadimos los paneles para la interfaz de usuario
        ExperimentPanel experimentPanel = new ExperimentPanel();
        PopulationPanel populationPanel = new PopulationPanel();

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, experimentPanel, populationPanel);
        splitPane.setDividerLocation(400);

        add(splitPane, BorderLayout.CENTER);
    }
}
