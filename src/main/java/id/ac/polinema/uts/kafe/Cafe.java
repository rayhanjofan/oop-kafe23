
package id.ac.polinema.uts.kafe;

import java.util.Locale;

public class Cafe {

    private String name;
    private MenuItem[] items;
    private int itemCount;
    private Customer[] customers;
    private int customerCount;

    public Cafe(String name) {
        this.name = name;
        this.items = new MenuItem[20];
        this.itemCount = 0;
        this.customers = new Customer[10];
        this.customerCount = 0;
    }

    public String getName() {
        return name;
    }

    public boolean addItem(MenuItem item) {
        if (item == null || itemCount >= items.length) {
            return false;
        }

        items[itemCount] = item;
        itemCount++;
        return true;
    }

    public boolean addCustomer(Customer customer) {
        if (customer == null
                || customerCount >= customers.length
                || findCustomer(customer.getCustomerId()) != null) {
            return false;
        }

        customers[customerCount] = customer;
        customerCount++;
        return true;
    }

    public MenuItem[] getItems() {
        MenuItem[] result = new MenuItem[itemCount];

        for (int i = 0; i < itemCount; i++) {
            result[i] = items[i];
        }

        return result;
    }

    public Customer[] getCustomers() {
        Customer[] result = new Customer[customerCount];

        for (int i = 0; i < customerCount; i++) {
            result[i] = customers[i];
        }

        return result;
    }

    public MenuItem findItemByName(String name) {
        if (name == null) {
            return null;
        }

        for (int i = 0; i < itemCount; i++) {
            if (items[i].getName().equalsIgnoreCase(name)) {
                return items[i];
            }
        }

        return null;
    }

    public Customer findCustomer(String customerId) {
        if (customerId == null) {
            return null;
        }

        for (int i = 0; i < customerCount; i++) {
            if (customers[i].getCustomerId().equals(customerId)) {
                return customers[i];
            }
        }

        return null;
    }

    public MenuItem[] searchByKeyword(String keyword) {
        if (keyword == null) {
            return new MenuItem[0];
        }

        String search = keyword.toLowerCase(Locale.ROOT);
        int matchCount = 0;

        for (int i = 0; i < itemCount; i++) {
            if (items[i].getName().toLowerCase(Locale.ROOT)
                    .contains(search)) {
                matchCount++;
            }
        }

        MenuItem[] result = new MenuItem[matchCount];
        int index = 0;

        for (int i = 0; i < itemCount; i++) {
            if (items[i].getName().toLowerCase(Locale.ROOT)
                    .contains(search)) {
                result[index] = items[i];
                index++;
            }
        }

        return result;
    }

    public boolean sell(String customerId, String itemName) {
        Customer customer = findCustomer(customerId);
        MenuItem item = findItemByName(itemName);

        if (customer == null
                || item == null
                || !customer.canOrder()
                || !item.isAvailable()) {
            return false;
        }

        if (!item.sell()) {
            return false;
        }

        if (!customer.addOrder()) {
            item.restock();
            return false;
        }

        return true;
    }

    public boolean refund(String customerId, String itemName) {
        Customer customer = findCustomer(customerId);
        MenuItem item = findItemByName(itemName);

        if (customer == null
                || item == null
                || item.isAvailable()
                || customer.getItemCount() <= 0) {
            return false;
        }

        item.restock();

        if (!customer.removeOrder()) {
            item.sell();
            return false;
        }

        return true;
    }
}
