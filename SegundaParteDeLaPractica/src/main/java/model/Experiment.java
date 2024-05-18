package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Experiment {
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<BacteriaPopulation> populations;

    public Experiment(String name, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.populations = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public List<BacteriaPopulation> getPopulations() {
        return populations;
    }

    public void addPopulation(BacteriaPopulation population) {
        this.populations.add(population);
    }

    public void removePopulation(BacteriaPopulation population) {
        this.populations.remove(population);
    }

    // Método para calcular la duración del experimento en días
    public int getDurationInDays() {
        return (int) startDate.until(endDate).getDays();
    }

    // Método para validar que el experimento no comience después de su finalización
    public boolean isValidExperiment() {
        return !endDate.isBefore(startDate);
    }
}
