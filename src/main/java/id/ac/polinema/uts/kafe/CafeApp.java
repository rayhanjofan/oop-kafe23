
package id.ac.polinema.uts.kafe;

import java.io.PrintStream;
import java.util.Scanner;

public class CafeApp {

    private Cafe cafe;
    private Scanner scanner;
    private PrintStream out;

    public CafeApp(Cafe cafe, Scanner scanner, PrintStream out) {
        this.cafe = cafe;
        this.scanner = scanner;
        this.out = out;
    }

    public void run() {
        boolean running = true;

        while (running) {
            showMainMenu();
            out.print("Select: ");

            if (!scanner.hasNextLine()) {
                break;
            }

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    showItems();
                    break;
                case "2":
                    addBeverage();
                    break;
                case "3":
                    addFood();
                    break;
                case "4":
                    addDessert();
                    break;
                case "5":
                    registerCustomer();
                    break;
                case "6":
                    sellItem();
                    break;
                case "7":
                    refundItem();
                    break;
                case "8":
                    searchMenu();
                    break;
                case "9":
                    showCustomers();
                    break;
                case "0":
                    out.println("Thank you");
                    running = false;
                    break;
                default:
                    out.println("Invalid option");
                    break;
            }
        }
    }

    private void showMainMenu() {
        out.println("=== " + cafe.getName() + " ===");
        out.println("1. Show menu");
        out.println("2. Add beverage");
        out.println("3. Add food");
        out.println("4. Add dessert");
        out.println("5. Register customer");
        out.println("6. Sell item");
        out.println("7. Refund item");
        out.println("8. Search menu");
        out.println("9. Show customers");
        out.println("0. Exit");
    }

    private void showItems() {
        MenuItem[] items = cafe.getItems();

        if (items.length == 0) {
            out.println("Menu is empty");
            return;
        }

        for (MenuItem item : items) {
            printItem(item);
        }
    }

    private void printItem(MenuItem item) {
        String status = item.isAvailable()
                ? "In stock"
                : "Sold out";

        out.println(
            item.describe()
            + " | " + status
            + " | Rp" + item.getTotalPrice()
        );
    }

    private void addBeverage() {
        out.print("Code: ");
        String code = scanner.nextLine();

        out.print("Name: ");
        String name = scanner.nextLine();

        out.print("Year: ");
        String yearInput = scanner.nextLine();

        // Periksa kode dan nama sebelum meminta data pemasok.
        if (code.trim().isEmpty() || name.trim().isEmpty()) {
            out.println("Invalid input");
            return;
        }

        Integer year = parseInteger(yearInput);

        if (year == null || year < 2000) {
            out.println("Invalid number");
            return;
        }

        out.print("Supplier name (blank for none): ");
        String supplierName = scanner.nextLine();

        Supplier supplier = null;

        if (!supplierName.trim().isEmpty()) {
            out.print("Supplier city: ");
            String supplierCity = scanner.nextLine();

            supplier = new Supplier(supplierName, supplierCity);
        }

        Beverage beverage =
                new Beverage(code, name, year, supplier);

        if (cafe.addItem(beverage)) {
            out.println("Item added");
        } else {
            out.println("Menu is full");
        }
    }

    private void addFood() {
        out.print("Name: ");
        String name = scanner.nextLine();

        if (name.trim().isEmpty()) {
            out.println("Invalid input");
            return;
        }

        out.print("Year: ");
        Integer year = parseInteger(scanner.nextLine());

        if (year == null || year < 2000) {
            out.println("Invalid number");
            return;
        }

        out.print("Prep time (minutes): ");
        Integer prepTime = parseInteger(scanner.nextLine());

        if (prepTime == null || prepTime <= 0) {
            out.println("Invalid number");
            return;
        }

        Food food = new Food(name, year, prepTime);

        if (cafe.addItem(food)) {
            out.println("Item added");
        } else {
            out.println("Menu is full");
        }
    }

    private void addDessert() {
        out.print("Name: ");
        String name = scanner.nextLine();

        if (name.trim().isEmpty()) {
            out.println("Invalid input");
            return;
        }

        out.print("Year: ");
        Integer year = parseInteger(scanner.nextLine());

        if (year == null || year < 2000) {
            out.println("Invalid number");
            return;
        }

        out.print("Calories: ");
        Integer calories = parseInteger(scanner.nextLine());

        if (calories == null || calories <= 0) {
            out.println("Invalid number");
            return;
        }

        Dessert dessert = new Dessert(name, year, calories);

        if (cafe.addItem(dessert)) {
            out.println("Item added");
        } else {
            out.println("Menu is full");
        }
    }

    private void registerCustomer() {
        out.print("Customer ID: ");
        String customerId = scanner.nextLine();

        out.print("Name: ");
        String name = scanner.nextLine();

        if (customerId.trim().isEmpty()
                || name.trim().isEmpty()) {
            out.println("Invalid input");
            return;
        }

        if (cafe.findCustomer(customerId) != null) {
            out.println("Customer ID already exists");
            return;
        }

        Customer customer = new Customer(customerId, name);

        if (cafe.addCustomer(customer)) {
            out.println("Customer registered");
        } else {
            out.println("Customer ID already exists");
        }
    }

    private void sellItem() {
        out.print("Customer ID: ");
        String customerId = scanner.nextLine();

        out.print("Item name: ");
        String itemName = scanner.nextLine();

        if (cafe.sell(customerId, itemName)) {
            out.println("Sale successful");
        } else {
            out.println("Sale failed");
        }
    }

    private void refundItem() {
        out.print("Customer ID: ");
        String customerId = scanner.nextLine();

        out.print("Item name: ");
        String itemName = scanner.nextLine();

        if (cafe.refund(customerId, itemName)) {
            out.println("Refund successful");
        } else {
            out.println("Refund failed");
        }
    }

    private void searchMenu() {
        out.print("Keyword: ");
        String keyword = scanner.nextLine();

        MenuItem[] results = cafe.searchByKeyword(keyword);

        if (results.length == 0) {
            out.println("No matches");
            return;
        }

        for (MenuItem item : results) {
            printItem(item);
        }
    }

    private void showCustomers() {
        Customer[] customers = cafe.getCustomers();

        if (customers.length == 0) {
            out.println("No customers");
            return;
        }

        for (Customer customer : customers) {
            out.println(
                customer.getCustomerId()
                + " - " + customer.getName()
                + " (" + customer.getItemCount() + " items)"
            );
        }
    }

    private Integer parseInteger(String value) {
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
