package id.ac.polinema.uts.kafe;

public class Cashier {

    public boolean sell(Customer customer, Beverage beverage) {
        if (customer == null || beverage == null) {
            return false;
        }
        if (!customer.canOrder() || !beverage.isAvailable()) {
            return false;
        }

        if (!beverage.sell()) {
            return false;
        }

        if (!customer.addOrder()) {
            return false;
        }
        return true;
    }

    public boolean refund(Customer customer, Beverage beverage) {
        if (customer == null || beverage == null) {
            return false;
        }

        if (beverage.isAvailable() || customer.getItemCount() == 0) {
            return false;
        }

        beverage.restock();

        if (!customer.removeOrder()) {
            beverage.sell();
            return false;
        }

        return true;
    }
}
