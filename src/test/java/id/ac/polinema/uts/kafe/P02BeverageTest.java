package id.ac.polinema.uts.kafe;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class P02BeverageTest {

    @Test
    @DisplayName("Constructor menyimpan code, name, dan year")
    void constructorAndGetters() {
        Beverage b = new Beverage("B-0001", "Cafe Latte", 2015);
        assertEquals("B-0001", b.getCode());
        assertEquals("Cafe Latte", b.getName());
        assertEquals(2015, b.getYear());
    }

    @Test
    @DisplayName("Minuman baru langsung tersedia (ada stok)")
    void newBeverageIsAvailable() {
        assertTrue(new Beverage("1", "Cafe Latte", 2015).isAvailable());
    }

    @Test
    @DisplayName("sell: berhasil sekali, lalu gagal karena sudah terjual")
    void sell() {
        Beverage b = new Beverage("1", "Cafe Latte", 2015);
        assertTrue(b.sell());
        assertFalse(b.isAvailable());
        assertFalse(b.sell());
    }

    @Test
    @DisplayName("restock membuat minuman tersedia lagi")
    void restock() {
        Beverage b = new Beverage("1", "Cafe Latte", 2015);
        b.sell();
        b.restock();
        assertTrue(b.isAvailable());
        assertTrue(b.sell());
    }

    @Test
    @DisplayName("Menjual satu minuman tidak mengubah minuman lain")
    void objectsAreIndependent() {
        Beverage a = new Beverage("1", "Cafe Latte", 2015);
        Beverage b = new Beverage("2", "Es Teh", 2001);
        a.sell();
        assertFalse(a.isAvailable());
        assertTrue(b.isAvailable());
        assertEquals("Es Teh", b.getName());
    }
}
