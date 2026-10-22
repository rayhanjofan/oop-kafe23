package id.ac.polinema.uts.kafe;

public class LoyaltyCard {
    private String number;

    public LoyaltyCard (String number) {
        if (number == null || number.trim().isEmpty()) {
            throw new IllegalArgumentException("Loyalty card number cannot be null or empty");
        }
        this.number = number;
    }

    public String getNumber() {
        return number;
    }
}
