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
public class Beverage {

    private String code;
    private String name;
    private int year;
    private boolean available;
    private Supplier supplier;

    /**
     * Membuat minuman baru yang masih ada stoknya (belum terjual).
     * Bagian 3: code tidak boleh kosong; name dan year divalidasi seperti
     * setter-nya.
     */
    public Beverage(String code, String name, int year) {
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
        if (code == null || code.trim().isEmpty()) {
            throw new IllegalArgumentException("Code cannot be null or empty");
        }
        this.code = code;
        this.available = true;

        setName(name);
        setYear(year);
    }

    public String getCode() {
        // TODO Bantuan: kembalikan data yang disimpan constructor.
        //
        // Kerangka:
        // return ____;
        return code;
    }

    public String getName() {
        // TODO Bantuan: sama seperti getCode.
        return name;
    }

    public int getYear() {
        // TODO Bantuan: sama seperti getCode.
        return year;
    }

    /** True jika minuman masih ada stok (belum terjual). */
    public boolean isAvailable() {
        // TODO Bantuan: field apa yang menyimpan status stok?
        //
        // Kerangka:
        // return ____;
        return available;
    }

    /**
     * Menjual minuman. Berhasil (true) jika masih ada stok. Jika sudah
     * terjual, hasilnya false dan keadaan minuman tidak berubah.
     */
    public boolean sell() {
        // TODO Bantuan: apa yang harus kamu periksa lebih dulu sebelum mengubah status?
        // Method ini punya dua jalur return yang berbeda.
        //
        // Kerangka:
        // if (____) {
        // return false;
        // }
        // ____ = false;
        // return true;
        if (!available) {
            return false;
        }
        available = false;
        return true;
    }

    /** Mengembalikan stok (misalnya karena refund): minuman bisa dijual lagi. */
    public void restock() {
        // TODO Bantuan: satu baris saja. Status apa yang berubah?
        //
        // Kerangka:
        // ____ = true;
        available = true;
    }

    /**
     * Bagian 3. Mengubah nama. Nama null, kosong, atau hanya spasi ditolak
     * dengan IllegalArgumentException, dan nama lama tetap.
     */
    public void setName(String name) {
        // TODO Bantuan: periksa dulu, baru isi field. Class String punya method untuk
        // memeriksa teks kosong atau hanya spasi. Jangan lupa kemungkinan null.
        //
        // Kerangka:
        // if (name == null || ____) {
        // throw new IllegalArgumentException("____");
        // }
        // this.name = name;
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.name = name;
    }

    /**
     * Bagian 3. Mengubah tahun peluncuran. Tahun di bawah 2000 ditolak dengan
     * IllegalArgumentException, dan tahun lama tetap.
     */
    public void setYear(int year) {
        // TODO Bantuan: pola yang sama dengan setName. Apa syarat tahun yang valid?
        //
        // Kerangka:
        // if (year ____ 2000) {
        // throw new IllegalArgumentException("____");
        // }
        // this.year = year;
        if (year < 2000) {
            throw new IllegalArgumentException("Year cannot be less than 2000");
        }
        this.year = year;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
}
