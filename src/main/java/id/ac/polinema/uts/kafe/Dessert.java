package id.ac.polinema.uts.kafe;

public class Dessert extends MenuItem {
    private int calories;

    public Dessert(String name, int year, int prepTime) {
        super(name, year);
        this.calories = prepTime;
    }

    public int getCalories() {
        return calories;
    }

}
