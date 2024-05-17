package model;

public class Cell {
    private int bacteriaCount;
    private int foodAmount;

    public Cell() {
        this.bacteriaCount = 0;
        this.foodAmount = 0;
    }

    public int getBacteriaCount() {
        return bacteriaCount;
    }

    public void setBacteriaCount(int bacteriaCount) {
        this.bacteriaCount = bacteriaCount;
    }

    public int getFoodAmount() {
        return foodAmount;
    }

    public void setFoodAmount(int foodAmount) {
        this.foodAmount = foodAmount;
    }

    public void addFood(int amount) {
        this.foodAmount += amount;
    }

    public void addBacteria(int count) {
        this.bacteriaCount += count;
    }

    public void removeBacteria(int count) {
        this.bacteriaCount -= count;
    }

    public void consumeFood(int amount) {
        this.foodAmount -= amount;
    }
}
