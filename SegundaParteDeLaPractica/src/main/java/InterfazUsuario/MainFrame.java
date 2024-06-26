package InterfazUsuario;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import model.FoodPattern;

import model.Experiment;
import model.BacteriaPopulation;
import storage.ExperimentLoader;
import storage.ExperimentSaver;

public class MainFrame extends JFrame {
    private JPanel experimentPanel;
    private JPanel populationPanel;
    private Experiment experiment;
    private File currentFile;


    public MainFrame(Experiment experiment) {
        this.experiment = experiment;
        setTitle("Simulación de Crecimiento Bacteriano");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(1, 2));

        // Crear menú
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu fileMenu = new JMenu("Archivo");
        menuBar.add(fileMenu);
        JMenuItem openMenuItem = new JMenuItem("Abrir");
        JMenuItem saveMenuItem = new JMenuItem("Guardar");
        JMenuItem saveAsMenuItem = new JMenuItem("Guardar como");
        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(saveAsMenuItem);
        JMenu experimentMenu = new JMenu("Experimento");
        menuBar.add(experimentMenu);
        JMenuItem createExperimentMenuItem = new JMenuItem("Crear Nuevo Experimento");
        JMenuItem addPopulationMenuItem = new JMenuItem("Añadir Población");
        JMenuItem deletePopulationMenuItem = new JMenuItem("Borrar Población");
        JMenuItem viewPopulationsMenuItem = new JMenuItem("Ver Poblaciones");
        experimentMenu.add(createExperimentMenuItem);
        experimentMenu.add(addPopulationMenuItem);
        experimentMenu.add(deletePopulationMenuItem);
        experimentMenu.add(viewPopulationsMenuItem);
        JMenu simulationMenu = new JMenu("Simulación");
        menuBar.add(simulationMenu);
        JMenuItem runSimulationMenuItem = new JMenuItem("Realizar Simulación");
        simulationMenu.add(runSimulationMenuItem);

        // Panel de Experimentos
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

        // Botones para realizar acciones en las poblaciones
        JButton viewDetailsButton = new JButton("Ver Detalles");
        JButton runSimButton = new JButton("Simular Población");

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2));
        buttonPanel.add(viewDetailsButton);
        buttonPanel.add(runSimButton);
        populationPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Agregar paneles al frame
        add(experimentPanel);
        add(populationPanel);

        // Action Listeners
        openMenuItem.addActionListener(e -> openFile());
        saveMenuItem.addActionListener(e -> saveFile());
        saveAsMenuItem.addActionListener(e -> saveFileAs());
        createExperimentMenuItem.addActionListener(e -> createNewExperiment());
        addPopulationMenuItem.addActionListener(e -> addPopulation());
        deletePopulationMenuItem.addActionListener(e -> deletePopulation());
        viewPopulationsMenuItem.addActionListener(e -> viewPopulations());
        runSimulationMenuItem.addActionListener(e -> runSimulation());
        viewDetailsButton.addActionListener(e -> viewPopulationDetails(populationList.getSelectedValue()));
        runSimButton.addActionListener(e -> runPopulationSimulation(populationList.getSelectedValue()));
    }
    private void openFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            currentFile = fileChooser.getSelectedFile();
            try (FileReader reader = new FileReader(currentFile)) {
                ExperimentLoader loader = new ExperimentLoader();
                experiment = loader.loadExperiment(reader);
                updateExperimentPanel();
                updatePopulationList();
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al abrir el archivo", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    private void saveFile() {
        if (currentFile != null) {
            try (FileWriter writer = new FileWriter(currentFile)) {
                ExperimentSaver saver = new ExperimentSaver();
                saver.saveExperiment(experiment, String.valueOf(writer));
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al guardar el archivo", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            saveFileAs();
        }
    }
    private void saveFileAs() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            currentFile = fileChooser.getSelectedFile();
            saveFile();
        }
    }
    private void createNewExperiment() {
        // Solicitar al usuario el nombre del experimento y las fechas de inicio y fin
        String experimentName = JOptionPane.showInputDialog(this, "Introduce el nombre del experimento:");
        LocalDate startDate = LocalDate.now(); // Fecha de inicio por defecto
        LocalDate endDate = LocalDate.now().plusDays(7); // Fecha de fin por defecto (una semana después de la fecha de inicio)

        // Crear un nuevo objeto Experiment con los datos proporcionados
        experiment = new Experiment(experimentName, startDate, endDate);

        // Actualizar los paneles con la información del nuevo experimento
        updateExperimentPanel();
    }

    private void addPopulation() {
        if (experiment != null) {
            String populationName = JOptionPane.showInputDialog(this, "Introduce el nombre de la nueva población:");
            if (populationName != null && !populationName.isEmpty()) {
                // Creamos una nueva población con el nombre proporcionado
                BacteriaPopulation newPopulation = new BacteriaPopulation(populationName, FoodPattern.LINEAR_INCREASE);
                // Agregamos la población al experimento
                experiment.addPopulation(newPopulation);
                // Actualizamos la lista de poblaciones en el panel
                updatePopulationList();
            }
        } else {
            JOptionPane.showMessageDialog(this, "No hay experimento creado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void deletePopulation() {
        if (experiment != null) {
            String populationName = JOptionPane.showInputDialog(this, "Introduce el nombre de la población a borrar:");
            if (populationName != null && !populationName.isEmpty()) {
                // Buscamos la población por su nombre
                BacteriaPopulation populationToDelete = experiment.getPopulationByName(populationName);
                if (populationToDelete != null) {
                    // Borramos la población del experimento
                    experiment.removePopulation(populationToDelete);
                    // Actualizamos la lista de poblaciones en el panel
                    updatePopulationList();
                } else {
                    JOptionPane.showMessageDialog(this, "La población especificada no existe.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "No hay experimento creado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void viewPopulations() {
        if (experiment != null) {
            StringBuilder populationsInfo = new StringBuilder("Poblaciones:\n");
            for (BacteriaPopulation population : experiment.getPopulations()) {
                populationsInfo.append(population.getName()).append(" - ").append(population.getFoodPattern()).append("\n");
            }
            JOptionPane.showMessageDialog(this, populationsInfo.toString(), "Poblaciones", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No hay experimento creado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void runSimulation() {
        // Comprobamos si hay un experimento para simular
        if (experiment != null) {
            // Aquí iría la lógica para ejecutar la simulación del experimento actual
            // Por ejemplo, podrías mostrar un mensaje de éxito
            JOptionPane.showMessageDialog(this, "Simulación ejecutada correctamente.", "Simulación", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No hay experimento creado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void viewPopulationDetails(String populationName) {
        if (populationName != null && !populationName.isEmpty()) {
            // Buscamos la población por su nombre
            BacteriaPopulation population = experiment.getPopulationByName(populationName);
            if (population != null) {
                // Mostramos los detalles de la población
                JOptionPane.showMessageDialog(this, "Nombre: " + population.getName() + "\nPatrón de Alimentación: " + population.getFoodPattern(), "Detalles de Población", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "La población especificada no existe.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Debes seleccionar una población.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void runPopulationSimulation(String populationName) {
        if (populationName != null && !populationName.isEmpty()) {
            // Aquí iría la lógica para ejecutar la simulación de la población específica
            // Por ejemplo, podrías mostrar un mensaje de éxito
            JOptionPane.showMessageDialog(this, "Simulación de población ejecutada correctamente.", "Simulación de Población", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Debes seleccionar una población.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateExperimentPanel() {
        JTextArea experimentInfo = new JTextArea();
        experimentInfo.setText("Nombre del Experimento: " + experiment.getName() + "\n" +
                "Fecha de Inicio: " + experiment.getStartDate() + "\n" +
                "Fecha de Fin: " + experiment.getEndDate());
        experimentInfo.setEditable(false);
        experimentPanel.add(new JScrollPane(experimentInfo), BorderLayout.CENTER);
        experimentPanel.revalidate();
        experimentPanel.repaint();
    }
    private void updatePopulationList() {
        DefaultListModel<String> populationListModel = new DefaultListModel<>();
        for (BacteriaPopulation population : experiment.getPopulations()) {
            populationListModel.addElement(population.getName() + " - " + population.getFoodPattern());
        }
        JList<String> populationList = new JList<>(populationListModel);
        populationPanel.add(new JScrollPane(populationList), BorderLayout.CENTER);
        populationPanel.revalidate();
        populationPanel.repaint();
    }
}

