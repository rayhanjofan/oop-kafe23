package id.ac.polinema.uts.kafe;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Menjalankan CafeApp dengan input skrip (bukan keyboard sungguhan),
 * lalu memeriksa teks yang dicetak.
 */
@Timeout(10)
class P08CafeAppTest {

    private static Object cafe() throws Throwable {
        return R.create("Cafe", "Polinema Cafe");
    }

    private static String run(Object cafe, String... lines) throws Throwable {
        String input = String.join("\n", lines) + "\n";
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(buffer, true, StandardCharsets.UTF_8);
        Object app = R.create("CafeApp", cafe, new Scanner(input), out);
        R.call(app, "run");
        return buffer.toString(StandardCharsets.UTF_8);
    }

    private static void assertHas(String output, String expected) {
        assertTrue(output.contains(expected),
                "Output harus memuat \"" + expected + "\". Output sebenarnya:\n" + output);
    }

    @Test
    @DisplayName("Menu tampil lengkap dan pilihan 0 mencetak 'Thank you'")
    void menuAndExit() throws Throwable {
        String out = run(cafe(), "0");
        assertHas(out, "=== Polinema Cafe ===");
        for (String line : new String[] {"1. Show menu", "2. Add beverage", "3. Add food", "4. Add dessert",
                "5. Register customer", "6. Sell item", "7. Refund item", "8. Search menu",
                "9. Show customers", "0. Exit"}) {
            assertHas(out, line);
        }
        assertHas(out, "Thank you");
    }

    @Test
    @DisplayName("Pilihan yang tidak dikenal mencetak 'Invalid option' lalu menu muncul lagi")
    void invalidChoice() throws Throwable {
        String out = run(cafe(), "x", "42", "0");
        assertHas(out, "Invalid option");
        assertEquals(3, out.split("0\\. Exit", -1).length - 1, "Menu harus dicetak ulang di setiap putaran");
    }

    @Test
    @DisplayName("Daftar kosong mencetak 'Menu is empty' dan 'No customers'")
    void emptyLists() throws Throwable {
        String out = run(cafe(), "1", "9", "0");
        assertHas(out, "Menu is empty");
        assertHas(out, "No customers");
    }

    @Test
    @DisplayName("Tambah minuman lalu tampilkan: describe, status, dan harga")
    void addBeverageAndList() throws Throwable {
        Object cafe = cafe();
        String out = run(cafe, "2", "B-1", "Cafe Latte", "2015", "Kopi Nusantara", "Malang", "1", "0");
        assertHas(out, "Item added");
        assertHas(out, "Cafe Latte (2015) from Kopi Nusantara | In stock | Rp25000");
        assertEquals(1, ((Object[]) R.call(cafe, "getItems")).length);
    }

    @Test
    @DisplayName("Tambah minuman tanpa pemasok: nama pemasok dikosongkan")
    void addBeverageWithoutSupplier() throws Throwable {
        String out = run(cafe(), "2", "B-2", "Es Teh", "2001", "", "1", "0");
        assertHas(out, "Item added");
        assertHas(out, "Es Teh (2001) from Unknown | In stock | Rp25000");
    }

    @Test
    @DisplayName("Tambah makanan dan dessert")
    void addFoodAndDessert() throws Throwable {
        String out = run(cafe(), "3", "Nasi Goreng", "2010", "15", "4", "Pisang Coklat", "2024", "300", "1", "0");
        assertHas(out, "Nasi Goreng (2010) [15 min] | In stock | Rp35000");
        assertHas(out, "Pisang Coklat (2024) | In stock | Rp20000");
    }

    @Test
    @DisplayName("Tahun yang bukan angka atau di bawah 2000 mencetak 'Invalid number' dan item tidak ditambah")
    void invalidYear() throws Throwable {
        Object cafe = cafe();
        String out = run(cafe, "2", "B-1", "Cafe Latte", "abc", "3", "Nasi Goreng", "-5", "15", "2", "B-3", "Es Teh", "1999", "0");
        assertHas(out, "Invalid number");
        assertEquals(0, ((Object[]) R.call(cafe, "getItems")).length);
    }

    @Test
    @DisplayName("Kode atau nama kosong mencetak 'Invalid input'")
    void blankInput() throws Throwable {
        Object cafe = cafe();
        String out = run(cafe, "2", "", "Cafe Latte", "2015", "0");
        assertHas(out, "Invalid input");
        assertEquals(0, ((Object[]) R.call(cafe, "getItems")).length);
    }

    @Test
    @DisplayName("Daftar pelanggan baru, ID kembar ditolak")
    void registerCustomer() throws Throwable {
        String out = run(cafe(), "5", "C001", "Budi", "5", "C001", "Sari", "9", "0");
        assertHas(out, "Customer registered");
        assertHas(out, "Customer ID already exists");
        assertHas(out, "C001 - Budi (0 items)");
    }

    @Test
    @DisplayName("Alur lengkap: tambah pelanggan dan item, jual, lihat status, refund")
    void sellAndRefund() throws Throwable {
        Object cafe = cafe();
        String out = run(cafe,
                "5", "C001", "Budi",
                "3", "Nasi Goreng", "2010", "15",
                "6", "C001", "Nasi Goreng",
                "1", "9",
                "7", "C001", "Nasi Goreng",
                "1", "9",
                "0");
        assertHas(out, "Sale successful");
        assertHas(out, "Nasi Goreng (2010) [15 min] | Sold out | Rp35000");
        assertHas(out, "C001 - Budi (1 items)");
        assertHas(out, "Refund successful");
        assertHas(out, "Nasi Goreng (2010) [15 min] | In stock | Rp35000");
        assertHas(out, "C001 - Budi (0 items)");
    }

    @Test
    @DisplayName("Jual dan refund yang tidak valid mencetak 'Sale failed' dan 'Refund failed'")
    void failures() throws Throwable {
        String out = run(cafe(), "6", "X", "Y", "7", "X", "Y", "0");
        assertHas(out, "Sale failed");
        assertHas(out, "Refund failed");
    }

    @Test
    @DisplayName("Cari item: tampilkan yang cocok, atau 'No matches'")
    void search() throws Throwable {
        Object cafe = cafe();
        R.call(cafe, "addItem", new Beverage("1", "Cafe Latte", 2015));
        R.call(cafe, "addItem", R.create("Food", "Nasi Goreng", 2010, 15));
        String out = run(cafe, "8", "latte", "8", "zzz", "0");
        assertHas(out, "Cafe Latte (2015) from Unknown");
        assertHas(out, "No matches");
        assertTrue(!out.contains("Nasi Goreng (2010)"), "Nasi Goreng tidak cocok dengan kata kunci latte");
    }

    @Test
    @DisplayName("Main mencetak sapaan, lalu menjalankan CafeApp dengan data contoh")
    void mainStartsTheApp() {
        PrintStream originalOut = System.out;
        InputStream originalIn = System.in;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buffer, true, StandardCharsets.UTF_8));
        System.setIn(new ByteArrayInputStream("1\n0\n".getBytes(StandardCharsets.UTF_8)));
        try {
            Main.main(new String[0]);
        } finally {
            System.setOut(originalOut);
            System.setIn(originalIn);
        }
        String out = buffer.toString(StandardCharsets.UTF_8);
        assertTrue(out.startsWith("Welcome to Polinema Cafe"),
                "Baris pertama output Main harus sapaan. Output sebenarnya:\n" + out);
        assertHas(out, "=== Polinema Cafe ===");
        assertHas(out, "Thank you");
        assertTrue(!out.contains("Menu is empty"),
                "Main harus mengisi data contoh sebelum menjalankan CafeApp");
    }
}
