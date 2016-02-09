package cek;

import database.Database;

import java.sql.ResultSet;
import java.sql.Timestamp;

/**
 * Created by Julio on 2/9/2016.
 */
public class CekKetersediaan {
    private final String path = "jdbc:mysql://localhost:3306/nandetech";
    private Database db;

    public CekKetersediaan(){
        db = new Database();
    }

    public void cek(String namaAlat, Timestamp tanggalPinjam){
        ResultSet rs;
        String query = "SELECT alat.id_alat, peminjaman.tanggal_peminjaman, peminjaman.tanggal_pengembalian FROM" +
                "peminjaman JOIN peminjaman_alat ON peminjaman.id_peminjaman=peminjaman_alat.id_peminjaman JOIN \n" +
                "alat ON alat.id_alat=peminjaman_alat.id_alat WHERE LCASE(alat.nama_alat)=LCASE("+namaAlat+")";
        db.connect(path);
        rs = db.fetchData(query);
        try{

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
