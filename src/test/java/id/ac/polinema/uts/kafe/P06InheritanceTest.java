package id.ac.polinema.uts.kafe;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Inheritance: Beverage, Food, dan Dessert adalah MenuItem (IS-A). */
class P06InheritanceTest {

    private static Object food() throws Throwable {
        return R.create("Food", "Nasi Goreng", 2010, 15);
    }

    private static Object dessert() throws Throwable {
        return R.create("Dessert", "Pisang Coklat", 2024, 300);
    }

    @Test
    @DisplayName("Beverage, Food, dan Dessert extends MenuItem")
    void superclass() {
        Class<?> item = R.cls("MenuItem");
        assertEquals(item, Beverage.class.getSuperclass(), "Beverage harus extends MenuItem");
        assertEquals(item, R.cls("Food").getSuperclass(), "Food harus extends MenuItem");
        assertEquals(item, R.cls("Dessert").getSuperclass(), "Dessert harus extends MenuItem");
    }

    @Test
    @DisplayName("MenuItem menyimpan name dan year sebagai protected, dan tidak ada field public")
    void itemFields() {
        Class<?> item = R.cls("MenuItem");
        for (Field f : item.getDeclaredFields()) {
            if (f.isSynthetic()) {
                continue;
            }
            assertFalse(Modifier.isPublic(f.getModifiers()), "Field " + f.getName() + " tidak boleh public");
        }
        assertTrue(Modifier.isProtected(field(item, "name").getModifiers()), "name harus protected");
        assertTrue(Modifier.isProtected(field(item, "year").getModifiers()), "year harus protected");
    }

    private static Field field(Class<?> c, String name) {
        try {
            return c.getDeclaredField(name);
        } catch (NoSuchFieldException e) {
            return org.junit.jupiter.api.Assertions.fail("Field " + name + " tidak ada di " + c.getSimpleName());
        }
    }

    @Test
    @DisplayName("Beverage tidak menyimpan ulang name, year, dan available (sudah ada di MenuItem)")
    void beverageDoesNotRepeatFields() {
        for (Field f : Beverage.class.getDeclaredFields()) {
            String n = f.getName();
            assertFalse(n.equals("name") || n.equals("year") || n.equals("available"),
                    "Field " + n + " sudah ada di MenuItem. Hapus dari Beverage.");
        }
        R.assertAllFieldsPrivate(Beverage.class);
    }

    @Test
    @DisplayName("Method umum dipindah ke MenuItem, bukan ditulis ulang di subclass")
    void methodsMovedUp() {
        for (String name : new String[] {"getName", "getYear", "isAvailable", "sell", "restock"}) {
            assertFalse(R.declaresMethod(Beverage.class, name), "Beverage tidak perlu mendeklarasikan " + name);
            assertFalse(R.declaresMethod(R.cls("Food"), name), "Food tidak perlu mendeklarasikan " + name);
            assertTrue(R.declaresMethod(R.cls("MenuItem"), name), "MenuItem harus punya " + name);
        }
    }

    @Test
    @DisplayName("MenuItem(name, year) menyimpan data dan memvalidasinya")
    void itemConstructor() throws Throwable {
        Object item = R.create("MenuItem", "Generic", 2000);
        assertEquals("Generic", R.call(item, "getName"));
        assertEquals(2000, R.call(item, "getYear"));
        assertEquals(true, R.call(item, "isAvailable"));
        assertThrows(IllegalArgumentException.class, () -> R.create("MenuItem", " ", 2000));
        assertThrows(IllegalArgumentException.class, () -> R.create("MenuItem", "Generic", 1999));
    }

    @Test
    @DisplayName("Food menyimpan waktu persiapan dan memakai getter turunan dari MenuItem")
    void foodKeepsPrepTime() throws Throwable {
        Object f = food();
        assertEquals("Nasi Goreng", R.call(f, "getName"));
        assertEquals(2010, R.call(f, "getYear"));
        assertEquals(15, R.call(f, "getPrepMinutes"));
        R.assertAllFieldsPrivate(R.cls("Food"));
    }

    @Test
    @DisplayName("Dessert menyimpan kalori dan memakai getter turunan")
    void dessertKeepsCalories() throws Throwable {
        Object ds = dessert();
        assertEquals("Pisang Coklat", R.call(ds, "getName"));
        assertEquals(300, R.call(ds, "getCalories"));
        R.assertAllFieldsPrivate(R.cls("Dessert"));
    }

    @Test
    @DisplayName("sell dan restock diwarisi oleh semua subclass")
    void inheritedBehaviour() throws Throwable {
        Object[] items = {new Beverage("1", "Cafe Latte", 2015), food(), dessert()};
        for (Object item : items) {
            assertEquals(true, R.call(item, "isAvailable"));
            assertEquals(true, R.call(item, "sell"));
            assertEquals(false, R.call(item, "sell"));
            R.call(item, "restock");
            assertEquals(true, R.call(item, "isAvailable"));
        }
    }

    @Test
    @DisplayName("Validasi setter ikut diwarisi: setName kosong ditolak di Food")
    void validationInherited() throws Throwable {
        Object f = food();
        assertThrows(IllegalArgumentException.class, () -> R.call(f, "setName", ""));
        assertThrows(IllegalArgumentException.class, () -> R.call(f, "setYear", 1999));
        assertEquals("Nasi Goreng", R.call(f, "getName"));
    }

    @Test
    @DisplayName("Setiap subclass adalah turunan MenuItem (extends)")
    void isInstance() throws Throwable {
        Class<?> item = R.cls("MenuItem");
        assertTrue(item.isInstance(new Beverage("1", "Cafe Latte", 2015)));
        assertTrue(item.isInstance(food()));
        assertTrue(item.isInstance(dessert()));
    }
}
