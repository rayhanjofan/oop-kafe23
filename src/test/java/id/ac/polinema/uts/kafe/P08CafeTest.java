package id.ac.polinema.uts.kafe;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Cafe berisi logika saja: tidak boleh mencetak apa pun. */
@Timeout(10)
class P08CafeTest {

    private static Object cafe() throws Throwable {
        return R.create("Cafe", "Polinema Cafe");
    }

    private static Object food(String name) throws Throwable {
        return R.create("Food", name, 2010, 20);
    }

    private static Object dessert(String name) throws Throwable {
        return R.create("Dessert", name, 2024, 100);
    }

    @Test
    @DisplayName("Cafe menyimpan nama dan semua field-nya private")
    void basics() throws Throwable {
        R.assertAllFieldsPrivate(R.cls("Cafe"));
        assertEquals("Polinema Cafe", R.call(cafe(), "getName"));
    }

    @Test
    @DisplayName("addItem menerima Beverage, Food, dan Dessert dalam satu array")
    void mixedItems() throws Throwable {
        Object cafe = cafe();
        Beverage latte = new Beverage("1", "Cafe Latte", 2015);
        Object food = food("Nasi Goreng");
        Object sweet = dessert("Pisang Coklat");
        assertEquals(true, R.call(cafe, "addItem", latte));
        assertEquals(true, R.call(cafe, "addItem", food));
        assertEquals(true, R.call(cafe, "addItem", sweet));
        Object[] items = (Object[]) R.call(cafe, "getItems");
        assertEquals(3, items.length, "getItems hanya mengembalikan item yang terisi");
        assertSame(latte, items[0]);
        assertSame(food, items[1]);
        assertSame(sweet, items[2]);
    }

    @Test
    @DisplayName("addItem menolak null dan menolak jika sudah penuh (20 item)")
    void itemCapacity() throws Throwable {
        Object cafe = cafe();
        assertEquals(false, R.call(cafe, "addItem", (Object) null));
        for (int i = 1; i <= 20; i++) {
            assertEquals(true, R.call(cafe, "addItem", dessert("Special " + i)));
        }
        assertEquals(false, R.call(cafe, "addItem", dessert("One too many")));
        assertEquals(20, ((Object[]) R.call(cafe, "getItems")).length);
    }

    @Test
    @DisplayName("addCustomer menolak id kembar dan null; findCustomer mengembalikan objek yang sama")
    void customers() throws Throwable {
        Object cafe = cafe();
        Customer budi = new Customer("C001", "Budi");
        assertEquals(true, R.call(cafe, "addCustomer", budi));
        assertEquals(false, R.call(cafe, "addCustomer", new Customer("C001", "Orang Lain")));
        assertEquals(false, R.call(cafe, "addCustomer", (Object) null));
        assertEquals(1, ((Object[]) R.call(cafe, "getCustomers")).length);
        assertSame(budi, R.call(cafe, "findCustomer", "C001"));
        assertNull(R.call(cafe, "findCustomer", "C999"));
    }

    @Test
    @DisplayName("addCustomer menolak jika sudah penuh (10 pelanggan)")
    void customerCapacity() throws Throwable {
        Object cafe = cafe();
        for (int i = 1; i <= 10; i++) {
            assertEquals(true, R.call(cafe, "addCustomer", new Customer("C" + i, "Customer " + i)));
        }
        assertEquals(false, R.call(cafe, "addCustomer", new Customer("C11", "Customer 11")));
    }

    @Test
    @DisplayName("findItemByName tidak peduli huruf besar-kecil; null jika tidak ada")
    void findItem() throws Throwable {
        Object cafe = cafe();
        Beverage latte = new Beverage("1", "Cafe Latte", 2015);
        R.call(cafe, "addItem", latte);
        assertSame(latte, R.call(cafe, "findItemByName", "cafe latte"));
        assertSame(latte, R.call(cafe, "findItemByName", "CAFE LATTE"));
        assertNull(R.call(cafe, "findItemByName", "Unknown"));
    }

    @Test
    @DisplayName("searchByKeyword mengembalikan semua item yang namanya memuat kata kunci")
    void search() throws Throwable {
        Object cafe = cafe();
        Beverage latte = new Beverage("1", "Cafe Latte", 2015);
        Beverage iced = new Beverage("2", "Iced Latte", 2012);
        Object food = food("Nasi Goreng");
        R.call(cafe, "addItem", latte);
        R.call(cafe, "addItem", food);
        R.call(cafe, "addItem", iced);
        Object[] found = (Object[]) R.call(cafe, "searchByKeyword", "latte");
        assertEquals(2, found.length);
        assertSame(latte, found[0]);
        assertSame(iced, found[1]);
        Object[] none = (Object[]) R.call(cafe, "searchByKeyword", "zzz");
        assertEquals(0, none.length, "Tanpa hasil, kembalikan array kosong, bukan null");
    }

    @Test
    @DisplayName("sell berhasil: item terjual dan jumlah pesanan Customer naik")
    void sellSuccess() throws Throwable {
        Object cafe = cafe();
        Customer budi = new Customer("C001", "Budi");
        Beverage latte = new Beverage("1", "Cafe Latte", 2015);
        R.call(cafe, "addCustomer", budi);
        R.call(cafe, "addItem", latte);
        assertEquals(true, R.call(cafe, "sell", "C001", "Cafe Latte"));
        assertEquals(false, latte.isAvailable());
        assertEquals(1, budi.getItemCount());
    }

    @Test
    @DisplayName("sell gagal untuk Customer atau nama yang tidak ada, dan item yang sudah terjual")
    void sellFailures() throws Throwable {
        Object cafe = cafe();
        R.call(cafe, "addCustomer", new Customer("C001", "Budi"));
        R.call(cafe, "addCustomer", new Customer("C002", "Sari"));
        R.call(cafe, "addItem", new Beverage("1", "Cafe Latte", 2015));
        assertEquals(false, R.call(cafe, "sell", "C999", "Cafe Latte"));
        assertEquals(false, R.call(cafe, "sell", "C001", "Unknown"));
        assertEquals(true, R.call(cafe, "sell", "C001", "Cafe Latte"));
        assertEquals(false, R.call(cafe, "sell", "C002", "Cafe Latte"));
    }

    @Test
    @DisplayName("sell gagal jika Customer sudah memesan 5 item")
    void sellLimit() throws Throwable {
        Object cafe = cafe();
        Customer budi = new Customer("C001", "Budi");
        R.call(cafe, "addCustomer", budi);
        for (int i = 1; i <= 6; i++) {
            R.call(cafe, "addItem", dessert("Special " + i));
        }
        for (int i = 1; i <= 5; i++) {
            assertEquals(true, R.call(cafe, "sell", "C001", "Special " + i));
        }
        assertEquals(false, R.call(cafe, "sell", "C001", "Special 6"));
        assertEquals(5, budi.getItemCount());
        Object sixth = R.call(cafe, "findItemByName", "Special 6");
        assertEquals(true, R.call(sixth, "isAvailable"));
    }

    @Test
    @DisplayName("refund berhasil lalu gagal jika item tidak sedang terjual atau data tidak ada")
    void refund() throws Throwable {
        Object cafe = cafe();
        Customer budi = new Customer("C001", "Budi");
        Object food = food("Nasi Goreng");
        R.call(cafe, "addCustomer", budi);
        R.call(cafe, "addItem", food);
        assertEquals(false, R.call(cafe, "refund", "C001", "Nasi Goreng"));
        R.call(cafe, "sell", "C001", "Nasi Goreng");
        assertEquals(true, R.call(cafe, "refund", "C001", "Nasi Goreng"));
        assertEquals(true, R.call(food, "isAvailable"));
        assertEquals(0, budi.getItemCount());
        assertEquals(false, R.call(cafe, "refund", "C999", "Nasi Goreng"));
        assertEquals(false, R.call(cafe, "refund", "C001", "Unknown"));
    }

    @Test
    @DisplayName("Cafe tidak boleh mencetak ke layar (pisahkan logika dan tampilan)")
    void cafeDoesNotPrint() throws Throwable {
        PrintStream original = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buffer, true, StandardCharsets.UTF_8));
        try {
            Object cafe = cafe();
            R.call(cafe, "addCustomer", new Customer("C001", "Budi"));
            R.call(cafe, "addItem", new Beverage("1", "Cafe Latte", 2015));
            R.call(cafe, "sell", "C001", "Cafe Latte");
            R.call(cafe, "refund", "C001", "Cafe Latte");
            R.call(cafe, "searchByKeyword", "latte");
            R.call(cafe, "getItems");
        } finally {
            System.setOut(original);
        }
        assertTrue(buffer.toString(StandardCharsets.UTF_8).isEmpty(),
                "Cafe hanya berisi logika. Semua System.out.println ada di CafeApp");
    }
}
