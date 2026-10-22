package id.ac.polinema.uts.kafe;

public class Food extends MenuItem {
    private int prepMinutes;
    
    public Food(String name, int year, int prepMinutes) {
        super(name, year);
        this.prepMinutes = prepMinutes;
    }

    public int getPrepMinutes() {
        return prepMinutes;
    }
}
