package InterfazUsuario;
import model.BacteriaPopulation;
import model.Experiment;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ExperimentPanel extends JPanel {
    private Experiment experiment;
    private JTable table;
    private DefaultTableModel tableModel;

    public ExperimentPanel(Experiment experiment) {
        this.experiment = experiment;

        setLayout(new BorderLayout());

        // Crear la tabla para mostrar las poblaciones de bacterias
        tableModel = new DefaultTableModel(new Object[]{"Nombre", "Fecha de inicio", "Número de bacterias"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Botones para ordenar las poblaciones de bacterias
        JPanel buttonPanel = new JPanel();
        add(buttonPanel, BorderLayout.SOUTH);

        JButton sortStartDateButton = new JButton("Ordenar por fecha de inicio");
        sortStartDateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                experiment.sortByStartDate();
                refreshTable();
            }
        });
        buttonPanel.add(sortStartDateButton);

        JButton sortNameButton = new JButton("Ordenar por nombre");
        sortNameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                experiment.sortByName();
                refreshTable();
            }
        });
        buttonPanel.add(sortNameButton);

        JButton sortBacteriaCountButton = new JButton("Ordenar por número de bacterias");
        sortBacteriaCountButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                experiment.sortByBacteriaCount();
                refreshTable();
            }
        });
        buttonPanel.add(sortBacteriaCountButton);

        // Mostrar las poblaciones de bacterias
        refreshTable();
    }

    // Método para actualizar la tabla con las poblaciones de bacterias
    private void refreshTable() {
        tableModel.setRowCount(0); // Limpiar la tabla antes de agregar las nuevas filas
        for (BacteriaPopulation population : experiment.getPopulations()) {
            tableModel.addRow(new Object[]{population.getName(), population.getStartDate(), population.getInitialBacteriaCount()});
        }
    }
}
