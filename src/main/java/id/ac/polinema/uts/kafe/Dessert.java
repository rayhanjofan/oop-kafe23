package id.ac.polinema.uts.kafe;

public class Dessert extends MenuItem {
    private int calories;

    public Dessert(String name, int year) {
        this(name, year, 250);
    }

    public Dessert(String name, int year, int calories) {
        super(name, year);
        setCalories(calories);
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories (int calories) {
        if (calories <= 0) {
            throw new IllegalArgumentException("Calories must be greater than zero");
        }
        this.calories = calories;
    }
}
