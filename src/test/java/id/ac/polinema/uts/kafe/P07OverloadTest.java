package id.ac.polinema.uts.kafe;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

/** Overloading: nama method atau constructor sama, parameter berbeda. */
class P07OverloadTest {

    @Test
    @DisplayName("MenuItem punya dua method addExtra dengan parameter berbeda")
    void twoAddExtra() {
        int count = 0;
        for (Method m : R.cls("MenuItem").getDeclaredMethods()) {
            if (m.getName().equals("addExtra")) {
                count++;
            }
        }
        assertEquals(2, count, "Harus ada addExtra() dan addExtra(int amount)");
    }

    @Test
    @DisplayName("addExtra() menambah Rp5000")
    void addExtraDefault() throws Throwable {
        Object food = R.create("Food", "Nasi Goreng", 2010, 15);
        assertEquals(35000, R.call(food, "getTotalPrice"));
        R.call(food, "addExtra");
        assertEquals(40000, R.call(food, "getTotalPrice"));
    }

    @Test
    @DisplayName("addExtra(int) menambah sebesar nilai yang diberikan dan bisa berulang")
    void addExtraAmount() throws Throwable {
        Beverage b = new Beverage("1", "Cafe Latte", 2015);
        assertEquals(25000, R.call(b, "getTotalPrice"));
        R.call(b, "addExtra", 3000);
        R.call(b, "addExtra", 2000);
        assertEquals(30000, R.call(b, "getTotalPrice"));
    }

    @Test
    @DisplayName("addExtra(int) menolak 0 dan negatif; total tidak berubah")
    void addExtraRejectsNonPositive() throws Throwable {
        Object m = R.create("Dessert", "Pisang Coklat", 2024, 300);
        assertThrows(IllegalArgumentException.class, () -> R.call(m, "addExtra", 0));
        assertThrows(IllegalArgumentException.class, () -> R.call(m, "addExtra", -4));
        assertEquals(20000, R.call(m, "getTotalPrice"));
    }

    @Test
    @DisplayName("Constructor Beverage(code, name, year, supplier) langsung mengisi pemasok")
    void beverageConstructorWithSupplier() throws Throwable {
        Supplier supplier = new Supplier("Kopi Nusantara", "Malang");
        Object b = R.create("Beverage", "1", "Cafe Latte", 2015, supplier);
        assertSame(supplier, R.call(b, "getSupplier"));
        assertEquals("Cafe Latte", R.call(b, "getName"));
        assertEquals("1", R.call(b, "getCode"));
        assertEquals(true, R.call(b, "isAvailable"));
    }

    @Test
    @DisplayName("Constructor Dessert(name, year) memberi kalori 250; versi 3 parameter tetap jalan")
    void dessertConstructors() throws Throwable {
        Object a = R.create("Dessert", "Pisang Coklat", 2024);
        assertEquals(250, R.call(a, "getCalories"));
        Object b = R.create("Dessert", "Pisang Coklat", 2024, 400);
        assertEquals(400, R.call(b, "getCalories"));
    }
}
