package id.ac.polinema.uts.kafe;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Overriding: subclass mengganti perilaku method yang diwarisi. */
class P07OverrideTest {

    private static Object item() throws Throwable {
        return R.create("MenuItem", "Generic", 2000);
    }

    private static Object food() throws Throwable {
        return R.create("Food", "Nasi Goreng", 2010, 15);
    }

    private static Object dessert() throws Throwable {
        return R.create("Dessert", "Pisang Coklat", 2024, 300);
    }

    @Test
    @DisplayName("price: MenuItem 20000, Beverage 25000, Food 35000, Dessert mewarisi 20000")
    void priceValues() throws Throwable {
        assertEquals(20000, R.call(item(), "price"));
        assertEquals(25000, R.call(new Beverage("1", "Cafe Latte", 2015), "price"));
        assertEquals(35000, R.call(food(), "price"));
        assertEquals(20000, R.call(dessert(), "price"));
    }

    @Test
    @DisplayName("Beverage dan Food meng-override price; Dessert tidak")
    void whoOverrides() {
        assertTrue(R.declaresMethod(Beverage.class, "price"), "Beverage harus override price");
        assertTrue(R.declaresMethod(R.cls("Food"), "price"), "Food harus override price");
        assertFalse(R.declaresMethod(R.cls("Dessert"), "price"),
                "Dessert tidak perlu override price, ia memakai versi MenuItem");
    }

    @Test
    @DisplayName("describe di MenuItem: 'name (year)'")
    void itemDescribe() throws Throwable {
        assertEquals("Generic (2000)", R.call(item(), "describe"));
    }

    @Test
    @DisplayName("describe di Beverage memakai super.describe() lalu menambah ' from pemasok'")
    void beverageDescribe() throws Throwable {
        Beverage b = new Beverage("1", "Cafe Latte", 2015);
        assertEquals("Cafe Latte (2015) from Unknown", R.call(b, "describe"));
        R.call(b, "setSupplier", new Supplier("Kopi Nusantara", "Malang"));
        assertEquals("Cafe Latte (2015) from Kopi Nusantara", R.call(b, "describe"));
    }

    @Test
    @DisplayName("describe di Food menambah waktu persiapan")
    void foodDescribe() throws Throwable {
        assertEquals("Nasi Goreng (2010) [15 min]", R.call(food(), "describe"));
    }

    @Test
    @DisplayName("describe di Dessert memakai versi MenuItem")
    void dessertDescribe() throws Throwable {
        assertEquals("Pisang Coklat (2024)", R.call(dessert(), "describe"));
    }

    @Test
    @DisplayName("toString mengembalikan describe() untuk semua jenis item")
    void toStringUsesDescribe() throws Throwable {
        Object[] items = {item(), new Beverage("1", "Cafe Latte", 2015), food(), dessert()};
        for (Object it : items) {
            assertEquals(R.call(it, "describe"), it.toString(),
                    "toString harus mengembalikan describe() pada " + it.getClass().getSimpleName());
        }
    }
}
