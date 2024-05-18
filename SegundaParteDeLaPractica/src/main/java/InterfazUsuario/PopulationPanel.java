package InterfazUsuario;

import javax.swing.*;
import java.awt.*;

public class PopulationPanel extends JPanel {

    public PopulationPanel() {
        setLayout(new BorderLayout());

        // Aquí podemos añadir componentes para gestionar las poblaciones de bacterias
        JLabel label = new JLabel("Panel de Poblaciones");
        add(label, BorderLayout.CENTER);
    }
}
