package id.ac.polinema.uts.kafe;

public class Counter {
    private String code;
    private int count;
    private Beverage[] beverages;

    public Counter(String code) {
        if (code == null || code.trim().isEmpty()) {
            throw new IllegalArgumentException("Counter code cannot be null or empty");
        }
        this.code = code;
        this.count = 0;
        this.beverages = new Beverage[5];
    }

    public String getCode() {
        return code;
    }

    public boolean addBeverage(Beverage beverage) {
        if (count < 5) {
            beverages[count] = beverage;
            count++;
            return true;
        }
        return false;   
    }

    public int getBeveragesCount() {
        return count;
    }


    public Beverage findByCode (String code) {
        for (int i = 0; i < count; i++) {
            if (beverages[i].getCode().equals(code)) {
                return beverages[i];
            }
        }
        return null;
    }

    public int countAvailable() {
        int availableCount = 0;
        for (int i = 0; i < count; i++) {
            if (beverages[i].isAvailable()) {
                availableCount++;
            }
        }
        return availableCount;
    }
}

