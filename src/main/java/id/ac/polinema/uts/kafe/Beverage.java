package id.ac.polinema.uts.kafe;

/**
 * Bagian 2 dan 3: minuman di menu kafe.
 *
 * Jangan ubah nama kelas, nama method, atau signature-nya. Autograder
 * memanggilnya persis seperti yang tertulis di sini.
 *
 * Field tidak dideklarasikan di sini: baca diagram kelas di README.
 * Method untuk bagian berikutnya (setSupplier, getSupplier, dan seterusnya)
 * kamu
 * tambahkan sendiri sesuai diagram di bagian tersebut.
 *
 * Kerangka field (lengkapi tipe dan nama dari diagram), letakkan di atas
 * constructor:
 * public String code;
 * public ____ name;
 * public ____ year;
 * public ____ available;
 */
public class Beverage extends MenuItem {

    private String code;
    private Supplier supplier;

    /**
     * Membuat minuman baru yang masih ada stoknya (belum terjual).
     * Bagian 3: code tidak boleh kosong; name dan year divalidasi seperti
     * setter-nya.
     */

    public Beverage(String code, String name, int year) {
        this(code, name, year, null);
    }

    public Beverage(String code, String name, int year, Supplier supplier) {
        // TODO Bantuan: isi semua field. Minuman baru: apakah sudah terjual atau belum?
        // Bagian 3: aturan validasi nama dan tahun sama dengan setter. Bisakah
        // constructor
        // memakai setter-nya?
        //
        // Kerangka (Bagian 2):
        // this.code = code;
        // this.name = ____;
        // this.year = ____;
        // this.available = ____;
        //
        // Kerangka (Bagian 3): ganti dua baris name dan year dengan pemanggilan setter.
        // setName(____);
        // setYear(____);
        super(name, year);
        if (code == null || code.trim().isEmpty()) {
            throw new IllegalArgumentException("Code cannot be null or empty");
        }
        this.code = code;
        this.supplier = supplier;
    }

    public String getCode() {
        // TODO Bantuan: kembalikan data yang disimpan constructor.
        //
        // Kerangka:
        // return ____;
        return code;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    @Override 
    public int price() {
        return 25000;
    }

    @Override 
    public String describe() {
        String supplierName = (supplier == null)
        ? "Unknown"
        : supplier.getName();

        return super.describe() + "from" + supplierName;
    }
}
