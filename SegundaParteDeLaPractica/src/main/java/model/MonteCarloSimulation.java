package model;

import java.util.Random;

public class MonteCarloSimulation {
    private static final int GRID_SIZE = 20;
    private static final int INITIAL_GRID_SIZE = 4;
    private Cell[][] grid;
    private Random random;

    public MonteCarloSimulation() {
        this.grid = new Cell[GRID_SIZE][GRID_SIZE];
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                grid[i][j] = new Cell();
            }
        }
        this.random = new Random();
    }

    public void initialize(BacteriaPopulation population) {
        int initialBacteriaCount = population.getInitialBacteriaCount();
        int initialBacteriaPerCell = initialBacteriaCount / (INITIAL_GRID_SIZE * INITIAL_GRID_SIZE);

        int start = (GRID_SIZE - INITIAL_GRID_SIZE) / 2;
        int end = start + INITIAL_GRID_SIZE;

        for (int i = start; i < end; i++) {
            for (int j = start; j < end; j++) {
                grid[i][j].setBacteriaCount(initialBacteriaPerCell);
            }
        }
    }

    // Método para ejecutar la simulación
    public void runSimulation(int days, FoodPattern foodPattern) {
        for (int day = 0; day < days; day++) {
            distributeFood(foodPattern, day);
            simulateDay();
        }
    }

    private void distributeFood(FoodPattern foodPattern, int day) {
        int foodAmount = getFoodAmountForDay(foodPattern, day);
        int foodPerCell = foodAmount / (GRID_SIZE * GRID_SIZE);

        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                grid[i][j].addFood(foodPerCell * 1000); // Cambio de miligramos a microgramos
            }
        }
    }


    private int getFoodAmountForDay(FoodPattern foodPattern, int day) {
        switch (foodPattern) {
            case CONSTANT:
                return 300000; // Cambio de miligramos a microgramos
            case LINEAR_INCREASE:
                return 300000 * day / 30; // Cambio de miligramos a microgramos
            case ALTERNATING_DAYS:
                return (day % 2 == 0) ? 300000 : 0; // Cambio de miligramos a microgramos
            case LINEAR_INCREASE_DECREASE:
                if (day <= 15) {
                    return 300000 * day / 15; // Cambio de miligramos a microgramos
                } else {
                    return 300000 * (30 - day) / 15; // Cambio de miligramos a microgramos
                }
            case CONSTANT_DAY_ALTERNATE:
                return (day % 2 == 0) ? 300000 : 0; // Cambio de miligramos a microgramos
            default:
                return 300000; // Cambio de miligramos a microgramos
        }
    }



    private void simulateDay() {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                Cell cell = grid[i][j];
                for (int k = 0; k < cell.getBacteriaCount(); k++) {
                    simulateBacteria(cell, i, j);
                }
            }
        }
    }

    private void simulateBacteria(Cell cell, int x, int y) {
        for (int step = 0; step < 10; step++) {
            if (cell.getFoodAmount() >= 100) {
                cell.consumeFood(20);
                int randomValue = random.nextInt(100);
                if (randomValue < 3) {
                    cell.removeBacteria(1);
                    return;
                } else if (randomValue >= 60) {
                    moveBacteria(cell, x, y, randomValue);
                }
            } else if (cell.getFoodAmount() >= 10) {
                cell.consumeFood(10);
                int randomValue = random.nextInt(100);
                if (randomValue < 6) {
                    cell.removeBacteria(1);
                    return;
                } else if (randomValue >= 20) {
                    moveBacteria(cell, x, y, randomValue);
                }
            } else {
                int randomValue = random.nextInt(100);
                if (randomValue < 20) {
                    cell.removeBacteria(1);
                    return;
                } else if (randomValue >= 60) {
                    moveBacteria(cell, x, y, randomValue);
                }
            }
        }
    }

    private void moveBacteria(Cell cell, int x, int y, int randomValue) {
        int newX = x, newY = y;
        if (randomValue >= 60 && randomValue < 65) newY++;
        else if (randomValue >= 65 && randomValue < 70) newX++;
        else if (randomValue >= 70 && randomValue < 75) newY--;
        else if (randomValue >= 75 && randomValue < 80) newX--;
        else if (randomValue >= 80 && randomValue < 85) { newX++; newY++; }
        else if (randomValue >= 85 && randomValue < 90) { newX++; newY--; }
        else if (randomValue >= 90 && randomValue < 95) { newX--; newY++; }
        else if (randomValue >= 95) { newX--; newY--; }

        if (newX >= 0 && newX < GRID_SIZE && newY >= 0 && newY < GRID_SIZE) {
            cell.removeBacteria(1);
            grid[newX][newY].addBacteria(1);
        }
    }
}
