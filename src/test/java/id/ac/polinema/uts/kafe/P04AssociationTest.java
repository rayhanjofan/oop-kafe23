package id.ac.polinema.uts.kafe;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

/** Asosiasi: Beverage mengenal Supplier, tetapi Supplier hidup sendiri. */
class P04AssociationTest {

    @Test
    @DisplayName("Minuman baru belum punya pemasok (null)")
    void newBeverageHasNoSupplier() throws Throwable {
        Beverage b = new Beverage("1", "Cafe Latte", 2015);
        assertNull(R.call(b, "getSupplier"));
    }

    @Test
    @DisplayName("getSupplier mengembalikan objek Supplier yang sama (asosiasi)")
    void sameSupplierObject() throws Throwable {
        Beverage b = new Beverage("1", "Cafe Latte", 2015);
        Supplier supplier = new Supplier("Kopi Nusantara", "Malang");
        R.call(b, "setSupplier", supplier);
        assertSame(supplier, R.call(b, "getSupplier"));
    }

    @Test
    @DisplayName("Dua minuman bisa berbagi satu Supplier")
    void beveragesShareSupplier() throws Throwable {
        Beverage a = new Beverage("1", "Cafe Latte", 2015);
        Beverage b = new Beverage("2", "Cafe Mocha", 2018);
        Supplier supplier = new Supplier("Kopi Nusantara", "Malang");
        R.call(a, "setSupplier", supplier);
        R.call(b, "setSupplier", supplier);
        assertSame(R.call(a, "getSupplier"), R.call(b, "getSupplier"));
        assertEquals("Kopi Nusantara", supplier.getName());
    }

    @Test
    @DisplayName("setSupplier(null) melepas pemasok")
    void clearSupplier() throws Throwable {
        Beverage b = new Beverage("1", "Cafe Latte", 2015);
        R.call(b, "setSupplier", new Supplier("Kopi Nusantara", "Malang"));
        R.call(b, "setSupplier", (Object) null);
        assertNull(R.call(b, "getSupplier"));
    }
}
