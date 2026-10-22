package id.ac.polinema.uts.kafe;

public class Food extends MenuItem {
    private int prepMinutes;
    
    public Food(String name, int year, int prepMinutes) {
        super(name, year);
        setPrepMinutes(prepMinutes);
    }

    public int getPrepMinutes() {
        return prepMinutes;
    }

    public void setPrepMinutes(int prepMinutes){
        if (prepMinutes <= 0) {
            throw new IllegalArgumentException("Preperation time must be greater than zero");
        }
        this.prepMinutes = prepMinutes;
    }

    @Override
    public int price() {
        return 35000;
    }

    @Override
    public String describe() {
        return super.describe() + " [" + prepMinutes +  " min]";
    }
}
