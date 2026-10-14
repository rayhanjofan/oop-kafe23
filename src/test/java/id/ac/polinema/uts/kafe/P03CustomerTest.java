package id.ac.polinema.uts.kafe;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class P03CustomerTest {

    @Test
    @DisplayName("Semua field Customer private")
    void fieldsPrivate() {
        R.assertAllFieldsPrivate(Customer.class);
    }

    @Test
    @DisplayName("Constructor menyimpan id dan nama; jumlah pesanan awal 0")
    void constructor() {
        Customer m = new Customer("C001", "Budi");
        assertEquals("C001", m.getCustomerId());
        assertEquals("Budi", m.getName());
        assertEquals(0, m.getItemCount());
    }

    @Test
    @DisplayName("Constructor menolak id atau nama null/kosong")
    void constructorValidates() {
        assertThrows(IllegalArgumentException.class, () -> new Customer("", "Budi"));
        assertThrows(IllegalArgumentException.class, () -> new Customer(null, "Budi"));
        assertThrows(IllegalArgumentException.class, () -> new Customer("C001", "  "));
        assertThrows(IllegalArgumentException.class, () -> new Customer("C001", null));
    }

    @Test
    @DisplayName("customerId dan itemCount read-only: tidak ada setter")
    void readOnlyFields() {
        assertFalse(R.hasPublicMethod(Customer.class, "setCustomerId"));
        assertFalse(R.hasPublicMethod(Customer.class, "setItemCount"));
    }

    @Test
    @DisplayName("setName menolak nama kosong dan menjaga nama lama")
    void setName() {
        Customer m = new Customer("C001", "Budi");
        assertThrows(IllegalArgumentException.class, () -> m.setName(""));
        assertThrows(IllegalArgumentException.class, () -> m.setName(null));
        assertEquals("Budi", m.getName());
        m.setName("Budi Santoso");
        assertEquals("Budi Santoso", m.getName());
    }

    @Test
    @DisplayName("addOrder sampai batas 5; pesanan ke-6 ditolak")
    void addOrderLimit() {
        Customer m = new Customer("C001", "Budi");
        for (int i = 0; i < 5; i++) {
            assertTrue(m.addOrder());
        }
        assertFalse(m.addOrder());
        assertEquals(5, m.getItemCount());
    }

    @Test
    @DisplayName("canOrder dihitung dari jumlah pesanan (derived getter)")
    void canOrderIsDerived() {
        Customer m = new Customer("C001", "Budi");
        assertTrue(m.canOrder());
        for (int i = 0; i < 4; i++) {
            m.addOrder();
        }
        assertTrue(m.canOrder());
        m.addOrder();
        assertFalse(m.canOrder());
        m.removeOrder();
        assertTrue(m.canOrder());
    }

    @Test
    @DisplayName("removeOrder tidak boleh membuat jumlah pesanan negatif")
    void removeOrderFloor() {
        Customer m = new Customer("C001", "Budi");
        assertFalse(m.removeOrder());
        assertEquals(0, m.getItemCount());
        m.addOrder();
        assertTrue(m.removeOrder());
        assertEquals(0, m.getItemCount());
    }
}
