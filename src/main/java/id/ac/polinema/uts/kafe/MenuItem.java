package id.ac.polinema.uts.kafe;

public class MenuItem {
    protected String name;
    protected int year;
    private boolean available;
    private int extraPrice;

    public MenuItem(String name, int year) {

        setName(name);
        setYear(year);
        this.available = true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        if (year < 2000) {
            throw new IllegalArgumentException("Year cannot be less than 2000");
        }
        this.year = year;
    }

    public boolean isAvailable() {
        return available;
    }

    public boolean sell() {
        if (!available) {
            return false;
        }
        available = false;
        return true;
    }

        public void restock() {
        available = true;
    }

    public int price() {
        return 20000;
    }

    public String describe() {
        return name +  " (" + year + ")";
    }

    public String toString() {
        return describe();
    }

    public void addExtra() {
        addExtra(5000);
    }

    public void addExtra(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Extra must be greater than zero ");
        }
        extraPrice += amount;
    }

    public int getTotalPrice() {
        return price() + extraPrice;
    }

}
