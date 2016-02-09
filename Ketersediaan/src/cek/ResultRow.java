package cek;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by Julio Savigny on 2/9/2016.
 */
public class ResultRow {
    public int getId_alat() {
        return id_alat;
    }

    public void setId_alat(int id_alat) {
        this.id_alat = id_alat;
    }

    public Timestamp getTanggal_peminjaman() {
        return tanggal_peminjaman;
    }

    public void setTanggal_peminjaman(Timestamp tanggal_peminjaman) {
        this.tanggal_peminjaman = tanggal_peminjaman;
    }

    public Timestamp getTanggal_pengembalian() {
        return tanggal_pengembalian;
    }

    public void setTanggal_pengembalian(Timestamp tanggal_pengembalian) {
        this.tanggal_pengembalian = tanggal_pengembalian;
    }

    public String getNama_alat() {
        return nama_alat;
    }

    public void setNama_alat(String nama_alat) {
        this.nama_alat = nama_alat;
    }

    public String getKondisi() {
        return kondisi;
    }

    public void setKondisi(String kondisi) {
        this.kondisi = kondisi;
    }

    public ResultRow(int id_alat, Timestamp tanggal_peminjaman, Timestamp tanggal_pengembalian, String nama_alat, String kondisi) {
        this.id_alat = id_alat;
        this.tanggal_peminjaman = tanggal_peminjaman;
        this.tanggal_pengembalian = tanggal_pengembalian;
        this.nama_alat = nama_alat;
        this.kondisi=kondisi;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public ResultRow(String availability, int id_alat, Timestamp tanggal_peminjaman, Timestamp tanggal_pengembalian, String nama_alat, String kondisi) {
        this.availability = availability;
        this.id_alat = id_alat;
        this.tanggal_peminjaman = tanggal_peminjaman;
        this.tanggal_pengembalian = tanggal_pengembalian;
        this.nama_alat = nama_alat;
        this.kondisi = kondisi;
    }

    private String availability;
    private int id_alat;
    private Timestamp tanggal_peminjaman;
    private Timestamp tanggal_pengembalian;
    private String nama_alat;
    private String kondisi;

}
