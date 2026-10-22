package id.ac.polinema.uts.kafe;

/**
 * Bagian 3: pelanggan kafe.
 *
 * Jangan ubah nama kelas, nama method, atau signature-nya. Autograder
 * memanggilnya persis seperti yang tertulis di sini.
 *
 * Field tidak dideklarasikan di sini: baca diagram kelas di README
 * (semua field harus private).
 *
 * Kerangka field (lengkapi dari diagram), letakkan di atas constructor:
 * private ____ customerId;
 * private ____ name;
 * private int itemCount; // nilai awal 0
 */
public class Customer {

    private String customerId;
    private String name;
    private int itemCount;
    private LoyaltyCard loyaltyCard;

    /**
     * Membuat pelanggan yang belum punya pesanan.
     * customerId dan name tidak boleh null atau kosong: lempar
     * IllegalArgumentException jika tidak valid.
     */
    public Customer(String customerId, String name) {
        // TODO Bantuan: validasi dulu sebelum mengisi field. Aturan nama sama dengan
        // setName.
        // Bagian 4: kartu loyalty juga dibuat di sini (lihat diagram Bagian 4).
        //
        // Kerangka:
        // if (customerId == null || ____) {
        // throw new IllegalArgumentException("____");
        // }
        // this.customerId = customerId;
        // setName(____);
        if (customerId == null || customerId.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer ID cannot be null or empty");
        }
        this.customerId = customerId;
        setName(name);
        this.itemCount = 0;
        this.loyaltyCard = new LoyaltyCard("LC-" + customerId);
    }

    public String getCustomerId() {
        // TODO Bantuan: kembalikan data yang disimpan constructor.
        return customerId;
    }

    public String getName() {
        // TODO Bantuan: sama seperti getCustomerId.
        return name;
    }

    /**
     * Nama tidak boleh null atau kosong: lempar IllegalArgumentException dan
     * biarkan nama lama.
     */
    public void setName(String name) {
        // TODO Bantuan: periksa dulu, baru isi field.
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

    public int getItemCount() {
        // TODO Bantuan: kembalikan jumlah item pesanan saat ini.
        return itemCount;
    }

    /** True jika pelanggan masih boleh memesan (batasnya 5 item per pesanan). */
    public boolean canOrder() {
        // TODO Bantuan: apakah perlu field baru, atau cukup dihitung dari data yang
        // sudah ada?
        //
        // Kerangka:
        // return itemCount ____ 5;
        return itemCount < 5;
    }

    /**
     * Menambah jumlah item pesanan. Jika sudah di batas, tidak berubah dan hasilnya
     * false.
     */
    public boolean addOrder() {
        // TODO Bantuan: periksa batas dulu. Adakah method lain di kelas ini yang sudah
        // menjawab pertanyaan "boleh atau tidak"?
        //
        // Kerangka:
        // if (!____) {
        // return false;
        // }
        // itemCount++;
        // return true;
        if (!canOrder()) {
            return false;
        }
        itemCount++;
        return true;
    }

    /**
     * Mengurangi jumlah item pesanan. Jika sudah tidak ada pesanan, tidak berubah
     * dan hasilnya false.
     */
    public boolean removeOrder() {
        // TODO Bantuan: batas bawah jumlah item adalah berapa?
        //
        // Kerangka:
        // if (itemCount == ____) {
        // return false;
        // }
        // itemCount--;
        // return true;
        if (itemCount == 0) {
            return false;
        }
        itemCount--;
        return true;
    }

    public LoyaltyCard getCard() {
        return loyaltyCard;
    }
}
