package id.ac.polinema.uts.kafe;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class P02SupplierTest {

    @Test
    @DisplayName("Constructor menyimpan nama dan kota, getter mengembalikannya")
    void constructorAndGetters() {
        Supplier a = new Supplier("Kopi Nusantara", "Malang");
        assertEquals("Kopi Nusantara", a.getName());
        assertEquals("Malang", a.getCity());
    }

    @Test
    @DisplayName("getInfo mengembalikan 'nama (kota)'")
    void getInfo() {
        Supplier a = new Supplier("Teh Tarik Co", "Medan");
        assertEquals("Teh Tarik Co (Medan)", a.getInfo());
    }

    @Test
    @DisplayName("Dua objek Supplier punya data sendiri-sendiri")
    void objectsAreIndependent() {
        Supplier a = new Supplier("Kopi Nusantara", "Malang");
        Supplier b = new Supplier("Teh Tarik Co", "Medan");
        assertNotEquals(a.getName(), b.getName());
        assertEquals("Kopi Nusantara", a.getName());
        assertEquals("Medan", b.getCity());
    }
}
