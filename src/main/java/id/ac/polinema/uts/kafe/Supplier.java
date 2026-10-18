package id.ac.polinema.uts.kafe;


/**
 * Bagian 2: pemasok minuman.
 *
 * Jangan ubah nama kelas, nama method, atau signature-nya. Autograder
 * memanggilnya persis seperti yang tertulis di sini.
 *
 * Field tidak dideklarasikan di sini: baca diagram kelas di README.
 * Di Bagian 2 field boleh public. Di Bagian 3 kamu akan menutupnya.
 *
 * Kerangka field (lengkapi tipe dan nama dari diagram), letakkan di atas constructor:
 *   public ____ name;
 *   public ____ city;
 */
    public class Supplier {

    public String name;
    public String city;

    /** Membuat pemasok dengan nama dan kota asal. */
    public Supplier(String name, String city) {
        // TODO Bantuan: nama parameter sama dengan nama field. Kata kunci apa yang
        // menunjuk ke objek yang sedang dibuat?
        //
        // Kerangka:
        //   this.name = ____;
        //   this.city = ____;
        this.name = name;
        this.city = city;
        
    }

    public String getName() {
        // TODO Bantuan: kembalikan data yang disimpan constructor.
        //
        // Kerangka:
        //   return ____;
        return name;
    }

    public String getCity() {
        // TODO Bantuan: sama seperti getName.
        return city;
    }

    /** Teks berbentuk "nama (kota)". Contoh: Kopi Nusantara (Malang). */
    public String getInfo() {
        // TODO Bantuan: gabungkan dua data dengan operator + dan tanda kurung dalam String.
        //
        // Kerangka:
        //   return name + " (" + ____ + ")";
        return name + " (" + city + ")";
    }
}
