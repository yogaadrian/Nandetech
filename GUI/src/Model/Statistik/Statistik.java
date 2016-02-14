package Model.Statistik;

import Model.Database.Database;

import java.sql.ResultSet;
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
    public void ShowStatistikPenggunaanAlat(String nama_alat){
        String query = "";
        ResultSet rs;
        ArrayList<ArrayList<String>> list = new ArrayList<>(100);
        query = "SELECT extract(year from tanggal_peminjaman) as yr, extract(month from tanggal_peminjaman) as mon,golongan, count(id_alat) " +
                "FROM peminjaman natural join peminjaman_alat natural join alat natural join user " +
                "WHERE nama_alat = \"" + nama_alat +
                "\" group by extract(year from tanggal_peminjaman), extract(month from tanggal_peminjaman),golongan order by 1, 2";
        db.connect(path);
        rs = db.fetchData(query);

        try {
            int i=0;
            ArrayList<String> name = new ArrayList<>(1);
            for(int k=0; k<rs.getMetaData().getColumnCount(); k++){
                name.add(rs.getMetaData().getColumnName(k+1));
            }
            list.add(name);
            while(rs.next()){
                int j=0;
                ArrayList<String> temp = new ArrayList<>(1);
                while(j<rs.getMetaData().getColumnCount()) {
                    temp.add(rs.getString(j + 1));
                    j++;
                }
                list.add(temp);
                i++;
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        db.closeDatabase();

        for(int i=0; i<list.size(); i++){
            for(int j=0; j<list.get(i).size(); j++){
                System.out.print(list.get(i).get(j) + "#");
            }
            System.out.println();
        }



    }
    public void ShowStatistikPenggunaanKelompok(String golongan){
        String query = "";
        ResultSet rs;
        ArrayList<ArrayList<String>> list = new ArrayList<>(100);
        query = "SELECT extract(year from tanggal_peminjaman) as yr, extract(month from tanggal_peminjaman) as mon,nama_alat, count(id_alat) " +
                "FROM peminjaman natural join peminjaman_alat natural join alat natural join user " +
                "WHERE golongan = \"" + golongan +
                "\" group by extract(year from tanggal_peminjaman), extract(month from tanggal_peminjaman),nama_alat order by 1, 2";
        db.connect(path);
        rs = db.fetchData(query);

        try {
            int i=0;
            ArrayList<String> name = new ArrayList<>(1);
            for(int k=0; k<rs.getMetaData().getColumnCount(); k++){
                name.add(rs.getMetaData().getColumnName(k+1));
            }
            list.add(name);
            while(rs.next()){
                int j=0;
                ArrayList<String> temp = new ArrayList<>(1);
                while(j<rs.getMetaData().getColumnCount()) {
                    temp.add(rs.getString(j + 1));
                    j++;
                }
                list.add(temp);
                i++;
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        db.closeDatabase();

        for(int i=0; i<list.size(); i++){
            for(int j=0; j<list.get(i).size(); j++){
                System.out.print(list.get(i).get(j) + "#");
            }
            System.out.println();
        }

    }
    public void ShowStatistikPerbaikanAlat(int id_alat){
        String query = "";
        ResultSet rs;
        ArrayList<ArrayList<String>> list = new ArrayList<>(100);
        query = "SELECT extract(year from tanggal_masuk) as yr, extract(month from tanggal_masuk) as mon, count(id_alat) " +
                "FROM perbaikan " +
                "WHERE id_alat = " + id_alat +
                " group by extract(year from tanggal_masuk), extract(month from tanggal_masuk)";
        db.connect(path);
        rs = db.fetchData(query);

        try {
            int i=0;
            ArrayList<String> name = new ArrayList<>(1);
            for(int k=0; k<rs.getMetaData().getColumnCount(); k++){
                name.add(rs.getMetaData().getColumnName(k+1));
            }
            list.add(name);
            while(rs.next()){
                int j=0;
                ArrayList<String> temp = new ArrayList<>(1);
                while(j<rs.getMetaData().getColumnCount()) {
                    temp.add(rs.getString(j + 1));
                    j++;
                }
                list.add(temp);
                i++;
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        db.closeDatabase();

        for(int i=0; i<list.size(); i++){
            for(int j=0; j<list.get(i).size(); j++){
                System.out.print(list.get(i).get(j) + "#");
            }
            System.out.println();
        }

    }
}
