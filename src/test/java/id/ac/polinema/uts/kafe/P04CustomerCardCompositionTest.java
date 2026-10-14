package id.ac.polinema.uts.kafe;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

/** Komposisi: LoyaltyCard dibuat di dalam Customer dan tidak datang dari luar. */
class P04CustomerCardCompositionTest {

    @Test
    @DisplayName("LoyaltyCard ada dan field-nya private")
    void cardClass() {
        R.assertAllFieldsPrivate(R.cls("LoyaltyCard"));
    }

    @Test
    @DisplayName("Customer punya kartu bernomor 'LC-' + customerId")
    void customerHasCard() throws Throwable {
        Customer m = new Customer("C001", "Budi");
        Object card = R.call(m, "getCard");
        assertNotNull(card, "Customer harus membuat LoyaltyCard sendiri di constructor");
        assertEquals("LC-C001", R.call(card, "getNumber"));
    }

    @Test
    @DisplayName("getCard selalu mengembalikan kartu yang sama untuk satu Customer")
    void sameCardEveryTime() throws Throwable {
        Customer m = new Customer("C001", "Budi");
        assertSame(R.call(m, "getCard"), R.call(m, "getCard"));
    }

    @Test
    @DisplayName("Dua Customer punya dua kartu berbeda")
    void differentCustomersDifferentCards() throws Throwable {
        Customer a = new Customer("C001", "Budi");
        Customer b = new Customer("C002", "Sari");
        assertNotSame(R.call(a, "getCard"), R.call(b, "getCard"));
        assertEquals("LC-C002", R.call(R.call(b, "getCard"), "getNumber"));
    }

    @Test
    @DisplayName("Constructor Customer tidak menerima LoyaltyCard dari luar (komposisi)")
    void cardIsNotPassedIn() {
        Class<?> card = R.cls("LoyaltyCard");
        for (Constructor<?> k : Customer.class.getConstructors()) {
            for (Class<?> p : k.getParameterTypes()) {
                org.junit.jupiter.api.Assertions.assertNotEquals(card, p,
                        "Customer harus MEMBUAT LoyaltyCard sendiri, bukan menerimanya dari parameter");
            }
        }
    }
}
