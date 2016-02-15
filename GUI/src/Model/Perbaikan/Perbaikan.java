package Model.Perbaikan;

import Model.Database.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by user on 09/02/2016.
 */
public class Perbaikan {
    private final String path = "jdbc:mysql://localhost:3306/nandetech";
    private Database db;
    private Date mulai;
    private Date selesai;
    private Timestamp mulaitime;
    private Timestamp selesaitime;
    public Perbaikan(){
        db = new Database();
    }

    public ArrayList<ArrayList<String>> semuaAlat (){
       // ArrayList<String> listAlat = new ArrayList<String>();
        String query = "";
        ResultSet rs;
        ResultSetMetaData rsmd = null;
        int i = 1;
        ArrayList<String> list = new ArrayList<String>();
        ArrayList<ArrayList<String>> listAlat = new ArrayList<ArrayList<String>>();
        //select semua alat
        query = "SELECT * " +
                "FROM alat ";
        db.connect(path);
        rs = db.fetchData(query);
        try {
            rsmd = rs.getMetaData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            while(rs.next()){
                for(int j=0; j<rsmd.getColumnCount(); j++) {
                    list.add(rs.getString(i));
                    i++;
                }
                listAlat.add(list);
                i=1;
                list = new ArrayList<String>();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(listAlat);
        return listAlat;
    }
    public ArrayList<String> tampilkanPerbaikan(int N){
        String query = "";
        ResultSet rs;
        ResultSetMetaData rsmd = null;
        int i = 1;
        ArrayList<String> list = new ArrayList<String>();
                //ID alat input yang dicari
                query = "SELECT * " +
                        "FROM alat " +
                        "WHERE id_alat = " + N;
        db.connect(path);
        rs = db.fetchData(query);
        try {
            rsmd = rs.getMetaData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            while(rs.next()){
                for(int j=0; j<rsmd.getColumnCount(); j++) {
                    list.add(rs.getString(i));
                    i++;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(list);
        System.out.println();
        if(list.get(3).equals("TIDAK RUSAK")){
            System.out.println("Status barang dengan id = "+list.get(0)+" "+ list.get(3)+"\nTidak memerlukan perbaikan");
        }
        else if(list.get(3).equals("RUSAK")){
            System.out.println("Status barang dengan id = "+list.get(0)+" "+ list.get(3)+"\n Memerlukan perbaikan");
        }
        else {
            System.out.println("Status barang dengan id = "+list.get(0)+" "+ list.get(3)+"\n Barang sedang dalam perbaikan");
        }
        return list;
    }
    public void mulaiPerbaikan(int N){
        try {
            Connection conn = DriverManager.getConnection(path, "root", "");
        mulai = new java.util.Date();
        mulaitime = new Timestamp(mulai.getTime());
        System.out.println(mulaitime);
        String query = "";
        //query mulai perbaikan alat
        query = "INSERT INTO perbaikan (id_alat,tanggal_masuk,tanggal_keluar)"+"values (?,NULL,NULL) ON DUPLICATE KEY UPDATE tanggal_masuk=?";
        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setInt(1, N);
        preparedStmt.setTimestamp(2, mulaitime);
        preparedStmt.execute();
        conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void selesaiPerbaikan(int N){
        try {
            Connection conn = DriverManager.getConnection(path, "root", "");
            selesai = new java.util.Date();
            selesaitime = new Timestamp(selesai.getTime());
            System.out.println(mulaitime);
            System.out.println(selesaitime);
            String query = "";
            //query selesai perbaikan alat
            query = "UPDATE perbaikan SET id_alat = ?, tanggal_masuk = ?, tanggal_keluar = ? WHERE id_alat = ?";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,N);
            preparedStmt.setTimestamp(2,mulaitime);
            preparedStmt.setTimestamp(3,selesaitime);
            preparedStmt.setInt(4,N);
            preparedStmt.execute();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
