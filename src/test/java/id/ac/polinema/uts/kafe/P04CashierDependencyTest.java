package id.ac.polinema.uts.kafe;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Dependensi: Cashier hanya memakai Customer dan Beverage lewat parameter. */
class P04CashierDependencyTest {

    private static Object cashier() throws Throwable {
        return R.create("Cashier");
    }

    @Test
    @DisplayName("Cashier tidak menyimpan apa pun sebagai field (dependensi, bukan asosiasi)")
    void noFields() {
        Class<?> c = R.cls("Cashier");
        assertEquals(0, c.getDeclaredFields().length,
                "Cashier hanya BERGANTUNG pada Customer dan Beverage lewat parameter, jangan simpan sebagai field");
    }

    @Test
    @DisplayName("sell berhasil: minuman terjual dan jumlah pesanan Customer naik")
    void sellSuccess() throws Throwable {
        Object l = cashier();
        Customer m = new Customer("C001", "Budi");
        Beverage b = new Beverage("1", "Cafe Latte", 2015);
        assertEquals(true, R.call(l, "sell", m, b));
        assertFalse(b.isAvailable());
        assertEquals(1, m.getItemCount());
    }

    @Test
    @DisplayName("sell gagal jika minuman sudah terjual; jumlah pesanan tidak berubah")
    void sellSoldOut() throws Throwable {
        Object l = cashier();
        Customer a = new Customer("C001", "Budi");
        Customer b = new Customer("C002", "Sari");
        Beverage latte = new Beverage("1", "Cafe Latte", 2015);
        R.call(l, "sell", a, latte);
        assertEquals(false, R.call(l, "sell", b, latte));
        assertEquals(0, b.getItemCount());
    }

    @Test
    @DisplayName("sell gagal jika Customer sudah memesan 5 minuman; minuman tetap tersedia")
    void sellCustomerAtLimit() throws Throwable {
        Object l = cashier();
        Customer m = new Customer("C001", "Budi");
        for (int i = 1; i <= 5; i++) {
            R.call(l, "sell", m, new Beverage("" + i, "Beverage " + i, 2000));
        }
        Beverage sixth = new Beverage("6", "Beverage 6", 2000);
        assertEquals(false, R.call(l, "sell", m, sixth));
        assertTrue(sixth.isAvailable());
        assertEquals(5, m.getItemCount());
    }

    @Test
    @DisplayName("refund berhasil: minuman tersedia lagi dan jumlah pesanan turun")
    void refundSuccess() throws Throwable {
        Object l = cashier();
        Customer m = new Customer("C001", "Budi");
        Beverage b = new Beverage("1", "Cafe Latte", 2015);
        R.call(l, "sell", m, b);
        assertEquals(true, R.call(l, "refund", m, b));
        assertTrue(b.isAvailable());
        assertEquals(0, m.getItemCount());
    }

    @Test
    @DisplayName("refund gagal untuk minuman yang belum terjual")
    void refundNotSold() throws Throwable {
        Object l = cashier();
        Customer m = new Customer("C001", "Budi");
        assertEquals(false, R.call(l, "refund", m, new Beverage("1", "Cafe Latte", 2015)));
        assertEquals(0, m.getItemCount());
    }
}
