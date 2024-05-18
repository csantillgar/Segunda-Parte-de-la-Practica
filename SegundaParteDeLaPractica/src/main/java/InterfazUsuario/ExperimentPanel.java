package InterfazUsuario;

import javax.swing.*;
import java.awt.*;

public class ExperimentPanel extends JPanel {

    public ExperimentPanel() {
        setLayout(new BorderLayout());

        // Aquí podemos añadir componentes para gestionar los experimentos
        JLabel label = new JLabel("Panel de Experimentos");
        add(label, BorderLayout.CENTER);
    }
}
