package id.ac.polinema.uts.kafe;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

class P03EncapsulationTest {

    @Test
    @DisplayName("Semua field Beverage private")
    void beverageFieldsPrivate() {
        R.assertAllFieldsPrivate(Beverage.class);
    }

    @Test
    @DisplayName("Semua field Supplier private")
    void supplierFieldsPrivate() {
        R.assertAllFieldsPrivate(Supplier.class);
    }

    @Test
    @DisplayName("code read-only: Beverage tidak punya setCode, dan status tidak bisa di-set langsung")
    void readOnlyFields() {
        assertFalse(R.hasPublicMethod(Beverage.class, "setCode"), "code tidak boleh punya setter");
        assertFalse(R.hasPublicMethod(Beverage.class, "setAvailable"),
                "status tersedia hanya boleh berubah lewat sell dan restock");
    }

    @Test
    @DisplayName("setName menolak null, kosong, dan hanya spasi; nama lama tetap")
    void setNameRejectsBlank() {
        Beverage b = new Beverage("1", "Cafe Latte", 2015);
        assertThrows(IllegalArgumentException.class, () -> b.setName(null));
        assertThrows(IllegalArgumentException.class, () -> b.setName(""));
        assertThrows(IllegalArgumentException.class, () -> b.setName("   "));
        assertEquals("Cafe Latte", b.getName());
    }

    @Test
    @DisplayName("setName menerima nama yang valid")
    void setNameAcceptsValid() {
        Beverage b = new Beverage("1", "Cafe Latte", 2015);
        b.setName("Cafe Mocha");
        assertEquals("Cafe Mocha", b.getName());
    }

    @Test
    @DisplayName("setYear menolak tahun di bawah 2000; tahun lama tetap")
    void setYearRejectsBefore2000() {
        Beverage b = new Beverage("1", "Cafe Latte", 2015);
        assertThrows(IllegalArgumentException.class, () -> b.setYear(1999));
        assertThrows(IllegalArgumentException.class, () -> b.setYear(-5));
        assertEquals(2015, b.getYear());
        b.setYear(2000);
        assertEquals(2000, b.getYear());
    }

    @Test
    @DisplayName("Constructor Beverage memvalidasi code, name, dan year")
    void constructorValidates() {
        assertThrows(IllegalArgumentException.class, () -> new Beverage("", "Cafe Latte", 2015));
        assertThrows(IllegalArgumentException.class, () -> new Beverage(null, "Cafe Latte", 2015));
        assertThrows(IllegalArgumentException.class, () -> new Beverage("1", " ", 2015));
        assertThrows(IllegalArgumentException.class, () -> new Beverage("1", "Cafe Latte", 1999));
    }
}
