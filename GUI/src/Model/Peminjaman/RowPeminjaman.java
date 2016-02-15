package Model.Peminjaman;

import javafx.scene.control.Button;

import java.sql.Time;
import java.sql.Timestamp;

/**
 * Created by jsavigny on 2/15/16.
 */
public class RowPeminjaman {
    public RowPeminjaman(int idPeminjaman, int idAlat, String idPeminjam, Timestamp tanggalPeminjaman, Timestamp tanggalPengembalian, String deskripsi) {
        this.idPeminjaman = idPeminjaman;
        this.idAlat = idAlat;
        this.idPeminjam = idPeminjam;
        this.tanggalPeminjaman = tanggalPeminjaman;
        this.tanggalPengembalian = tanggalPengembalian;
        this.deskripsi = deskripsi;
    }

    public int getIdPeminjaman() {
        return idPeminjaman;
    }

    public void setIdPeminjaman(int idPeminjaman) {
        this.idPeminjaman = idPeminjaman;
    }

    public int getIdAlat() {
        return idAlat;
    }

    public void setIdAlat(int idAlat) {
        this.idAlat = idAlat;
    }

    public String getIdPeminjam() {
        return idPeminjam;
    }

    public void setIdPeminjam(String idPeminjam) {
        this.idPeminjam = idPeminjam;
    }

    public Timestamp getTanggalPeminjaman() {
        return tanggalPeminjaman;
    }

    public void setTanggalPeminjaman(Timestamp tanggalPeminjaman) {
        this.tanggalPeminjaman = tanggalPeminjaman;
    }

    public Timestamp getTanggalPengembalian() {
        return tanggalPengembalian;
    }

    public void setTanggalPengembalian(Timestamp tanggalPengembalian) {
        this.tanggalPengembalian = tanggalPengembalian;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }


    private int idPeminjaman;
    private int idAlat;
    private String idPeminjam;
    private Timestamp tanggalPeminjaman;
    private Timestamp tanggalPengembalian;
    private String deskripsi;

}