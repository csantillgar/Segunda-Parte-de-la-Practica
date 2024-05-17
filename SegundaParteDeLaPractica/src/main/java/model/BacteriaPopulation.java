package model;

import java.time.LocalDate;

public class BacteriaPopulation implements Comparable<BacteriaPopulation> {
    private String name;
    private LocalDate startDate;
    private int initialBacteriaCount;
    private FoodPattern foodPattern;

    public BacteriaPopulation(String name, LocalDate startDate, int initialBacteriaCount, FoodPattern foodPattern) {
        this.name = name;
        this.startDate = startDate;
        this.initialBacteriaCount = initialBacteriaCount;
        this.foodPattern = foodPattern;
    }

    public String getName() {
        return name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public int getInitialBacteriaCount() {
        return initialBacteriaCount;
    }

    public FoodPattern getFoodPattern() {
        return foodPattern;
    }

    @Override
    public int compareTo(BacteriaPopulation other) {
        return this.startDate.compareTo(other.startDate);
    }
}
