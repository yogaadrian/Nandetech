package Model.Statistik;

import Model.Database.Database;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by yoga on 2/9/2016.
 */
public class Statistik {
    private final String path = "jdbc:mysql://localhost:3306/nandetech";
    private Database db;

    public Statistik(){
        db = new Database();
    }
    public ArrayList<String> ShowStatistikPenggunaanAlat(String nama_alat) throws SQLException {
        ArrayList<String> listans = new ArrayList<String>();
        ResultSetMetaData rsmd = null;
        String query = "";
        ResultSet rs;
        query = "SELECT extract(year from tanggal_peminjaman) as yr, extract(month from tanggal_peminjaman) as mon,golongan, count(id_alat) " +
                "FROM peminjaman natural join peminjaman_alat natural join alat natural join user " +
                "WHERE nama_alat = \"" + nama_alat +
                "\" group by extract(year from tanggal_peminjaman), extract(month from tanggal_peminjaman),golongan order by 1, 2";
        db.connect(path);
        rs = db.fetchData(query);
        rsmd = rs.getMetaData();
        try {

            while(rs.next()){
                for (int j = 0; j < rsmd.getColumnCount(); j++) {
                    listans.add(rs.getString(j+1));
                }
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        db.closeDatabase();

        return listans;
    }
    public ArrayList<String> ShowStatistikPenggunaanKelompok(String golongan) throws SQLException {
        ArrayList<String> listans = new ArrayList<String>();
        ResultSetMetaData rsmd = null;
        String query = "";
        ResultSet rs;
        query = "SELECT extract(year from tanggal_peminjaman) as yr, extract(month from tanggal_peminjaman) as mon,nama_alat, count(id_alat) " +
                "FROM peminjaman natural join peminjaman_alat natural join alat natural join user " +
                "WHERE golongan = \"" + golongan +
                "\" group by extract(year from tanggal_peminjaman), extract(month from tanggal_peminjaman),nama_alat order by 1, 2";
        db.connect(path);
        rs = db.fetchData(query);
        rsmd = rs.getMetaData();
        try {
            while(rs.next()){
                for (int j = 0; j < rsmd.getColumnCount(); j++) {
                    listans.add(rs.getString(j+1));
                }
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        db.closeDatabase();

        return listans;
    }
    public ArrayList<String> ShowStatistikPerbaikanAlat(int id_alat) throws SQLException {
        ArrayList<String> listans = new ArrayList<String>();
        ResultSetMetaData rsmd = null;
        String query = "";
        ResultSet rs;
        query = "SELECT extract(year from tanggal_masuk) as yr, extract(month from tanggal_masuk) as mon, count(id_alat) " +
                "FROM perbaikan " +
                "WHERE id_alat = " + id_alat +
                " group by extract(year from tanggal_masuk), extract(month from tanggal_masuk)";
        db.connect(path);
        rs = db.fetchData(query);
        rsmd = rs.getMetaData();

        try {
            while(rs.next()){
                for (int j = 0; j < rsmd.getColumnCount(); j++) {
                    listans.add(rs.getString(j+1));
                }
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        db.closeDatabase();


    return listans;
    }
}
