
package id.ac.polinema.uts.kafe;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        // 1. Menampilkan sapaan pertama
        System.out.println("Welcome to Polinema Cafe");

        // 2. Membuat objek Cafe
        Cafe cafe = new Cafe("Polinema Cafe");

        // 3. Membuat data pemasok
        Supplier supplier = new Supplier(
            "Kopi Nusantara",
            "Malang"
        );

        // 4. Menambahkan contoh minuman
        Beverage beverage = new Beverage(
            "B001",
            "Cafe Latte",
            2015,
            supplier
        );
        cafe.addItem(beverage);

        // 5. Menambahkan contoh makanan
        Food food = new Food(
            "Nasi Goreng",
            2010,
            15
        );
        cafe.addItem(food);

        // 6. Menambahkan contoh dessert
        Dessert dessert = new Dessert(
            "Pisang Coklat",
            2024,
            300
        );
        cafe.addItem(dessert);

        // 7. Mendaftarkan pelanggan contoh
        Customer customer = new Customer(
            "C001",
            "Budi"
        );
        cafe.addCustomer(customer);

        // 8. Membuat dan menjalankan aplikasi konsol
        CafeApp app = new CafeApp(
            cafe,
            new Scanner(System.in),
            System.out
        );

        app.run();
    }
}
