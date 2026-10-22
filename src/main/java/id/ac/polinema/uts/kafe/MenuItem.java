package id.ac.polinema.uts.kafe;

public class MenuItem {
    protected String name;
    protected int year;
    private boolean available;

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
        // TODO Bantuan: pola yang sama dengan setName. Apa syarat tahun yang valid?
        //
        // Kerangka:
        // if (year ____ 2000) {
        // throw new IllegalArgumentException("____");
        // }
        // this.year = year;
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

}
