package Model.Peminjaman;

import Model.Database.Database;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by acel on 16-Feb-16.
 */
public class RowAlat {
    private int id_alat;
    private String nama_alat;
    private BooleanProperty checked;

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getKondisi() {
        return kondisi;
    }

    public void setKondisi(String kondisi) {
        this.kondisi = kondisi;
    }

    private String lokasi;
    private String kondisi;

    private final String path = "jdbc:mysql://localhost:3306/nandetech";

    public RowAlat(){
        id_alat = 0;
        nama_alat = "";
        checked = new SimpleBooleanProperty(false);
        lokasi = "";
        kondisi = "";
    }

    public RowAlat(int id_alat, String nama_alat, boolean checked) {
        this.id_alat = id_alat;
        this.nama_alat = nama_alat;
        this.checked = new SimpleBooleanProperty(checked);
    }

    public RowAlat(int id_alat, String nama_alat, String lokasi, String kondisi) {
        this.id_alat = id_alat;
        this.nama_alat = nama_alat;
        this.lokasi = lokasi;
        this.kondisi = kondisi;
    }

    public int getId_alat() {
        return id_alat;
    }

    public void setId_alat(int id_alat) {
        this.id_alat = id_alat;
    }

    public String getNama_alat() {
        return nama_alat;
    }

    public void setNama_alat(String nama_alat) {
        this.nama_alat = nama_alat;
    }

    public BooleanProperty isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked.set(checked);
    }

    public void tambahAlat(String nama_alat, String lokasi, String kondisi){
        Database db = new Database();
        db.connect(path);

        boolean unique = false;
        int id_alat = 1;
        String query;

        while(!unique){
            query = "SELECT * " +
                    "FROM alat " +
                    "WHERE id_alat = " + id_alat;
            ResultSet rs = db.fetchData(query);
            try {
                rs.next();
                System.out.println(query);
                System.out.println(id_alat);
                if(rs.getRow() == 0){
                    unique = true;
                } else {
                    id_alat++;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        query = "INSERT INTO alat " +
                "VALUES (" + id_alat + ", '" + nama_alat + "', '" + lokasi + "', '" + kondisi + "')";

        System.out.println(query);
        db.changeData(query);
        db.closeDatabase();
    }

    public void ubahAlat(int id_alat, String nama_baru, String lokasi_baru, String kondisi_baru){
        Database db = new Database();
        db.connect(path);

        String query = "UPDATE alat " +
                "SET nama_alat = '" + nama_baru + "', " +
                "lokasi = '" + lokasi_baru + "', " +
                "kondisi = '" + kondisi_baru + "'" +
                "WHERE id_alat = " + id_alat;

        System.out.println(query);
        db.changeData(query);

        db.closeDatabase();
    }

    public void hapusAlat(int id_alat){
        Database db = new Database();
        db.connect(path);

        String query = "DELETE FROM alat " +
                "WHERE id_alat = " + id_alat;
        System.out.println(query);
        db.changeData(query);

        db.closeDatabase();
    }

    public ArrayList<ArrayList<String>> tampilkanAlat(String N, int pilihan){
        Database db = new Database();

        String query = "";
        ResultSet rs;
        ArrayList<ArrayList<String>> list = new ArrayList<>(1);

        switch(pilihan){
            case 1:
                //ID Alat
                query = "SELECT * " +
                        "FROM alat " +
                        "WHERE id_alat = " + N;
                break;
            case 2:
                //Nama Alat
                query = "SELECT * " +
                        "FROM alat " +
                        "WHERE nama_alat LIKE '%" + N + "%'";
                break;
            case 3:
                //Lokasi Alat
                query = "SELECT * " +
                        "FROM alat " +
                        "WHERE lokasi = '" + N + "'";
                break;
            case 4:
                //Kondisi Alat
                query = "SELECT * " +
                        "FROM alat " +
                        "WHERE kondisi = '" + N + "'";
                break;
        }

        System.out.println(query);
        db.connect(path);
        rs = db.fetchData(query);

        try {
            int i=0;
            /* Add table header */
            /*
            ArrayList<String> name = new ArrayList<>(1);
            for(int k=0; k<rs.getMetaData().getColumnCount(); k++){
                name.add(rs.getMetaData().getColumnName(k+1));
            }
            list.add(name);
            */
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
        return list;
    }

    public boolean exists(int id_alat){
        Database db = new Database();
        db.connect(path);

        String query = "SELECT * " +
                "FROM alat " +
                "WHERE id_alat = " + id_alat;
        boolean retval = true;
        ResultSet rs = db.fetchData(query);
        try {
            rs.next();
            System.out.println(query);
            retval = rs.getRow() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        db.closeDatabase();

        return retval;
    }
}
