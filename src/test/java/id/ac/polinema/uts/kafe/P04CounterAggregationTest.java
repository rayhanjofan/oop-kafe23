package id.ac.polinema.uts.kafe;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Agregasi: Counter menyimpan Beverage, tetapi Beverage ada di luar Counter juga. */
class P04CounterAggregationTest {

    private static Object counter() throws Throwable {
        return R.create("Counter", "A1");
    }

    @Test
    @DisplayName("Counter ada, semua field-nya private, dan menyimpan kode konter")
    void basics() throws Throwable {
        R.assertAllFieldsPrivate(R.cls("Counter"));
        Object s = counter();
        assertEquals("A1", R.call(s, "getCode"));
        assertEquals(0, R.call(s, "getBeverageCount"));
    }

    @Test
    @DisplayName("addBeverage menambah minuman dan getBeverageCount ikut naik")
    void addBeverage() throws Throwable {
        Object s = counter();
        assertEquals(true, R.call(s, "addBeverage", new Beverage("1", "Cafe Latte", 2015)));
        assertEquals(true, R.call(s, "addBeverage", new Beverage("2", "Es Teh", 2001)));
        assertEquals(2, R.call(s, "getBeverageCount"));
    }

    @Test
    @DisplayName("Kapasitas konter 5 minuman; minuman ke-6 ditolak")
    void capacity() throws Throwable {
        Object s = counter();
        for (int i = 1; i <= 5; i++) {
            assertEquals(true, R.call(s, "addBeverage", new Beverage("" + i, "Beverage " + i, 2000)));
        }
        assertEquals(false, R.call(s, "addBeverage", new Beverage("6", "Beverage 6", 2000)));
        assertEquals(5, R.call(s, "getBeverageCount"));
    }

    @Test
    @DisplayName("addBeverage(null) ditolak")
    void rejectsNull() throws Throwable {
        Object s = counter();
        assertEquals(false, R.call(s, "addBeverage", (Object) null));
        assertEquals(0, R.call(s, "getBeverageCount"));
    }

    @Test
    @DisplayName("findByCode mengembalikan objek Beverage yang sama (agregasi), atau null")
    void findByCode() throws Throwable {
        Object s = counter();
        Beverage latte = new Beverage("111", "Cafe Latte", 2015);
        Beverage tea = new Beverage("222", "Es Teh", 2001);
        R.call(s, "addBeverage", latte);
        R.call(s, "addBeverage", tea);
        assertSame(tea, R.call(s, "findByCode", "222"));
        assertSame(latte, R.call(s, "findByCode", "111"));
        assertNull(R.call(s, "findByCode", "999"));
    }

    @Test
    @DisplayName("countAvailable mengikuti status minuman yang terjual lewat referensi luar")
    void countAvailable() throws Throwable {
        Object s = counter();
        Beverage a = new Beverage("1", "Cafe Latte", 2015);
        Beverage b = new Beverage("2", "Es Teh", 2001);
        R.call(s, "addBeverage", a);
        R.call(s, "addBeverage", b);
        assertEquals(2, R.call(s, "countAvailable"));
        assertTrue(a.sell());
        assertEquals(1, R.call(s, "countAvailable"));
        a.restock();
        assertEquals(2, R.call(s, "countAvailable"));
        assertTrue(b.isAvailable());
    }
}
