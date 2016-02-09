import database.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
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
    public Perbaikan(){
        db = new Database();
    }
    public void tampilkanPerbaikan(int N){
        String query = "";
        ResultSet rs;
        ArrayList<ArrayList<String>> list = new ArrayList<>(1);
                //ID alat input yang dicari
                query = "SELECT * " +
                        "FROM alat " +
                        "WHERE id_peminjaman = " + N;
        db.connect(path);
        rs = db.fetchData(query);

       /* try {
        //masukkan ke list
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        db.closeDatabase();
       /*
       //Tulis ke layar isi list
        for(int i=0; i<list.size(); i++){
            for(int j=0; j<list.get(i).size(); j++){
                System.out.print(list.get(i).get(j) + "#");
            }
            System.out.println();
        }*/
    }
    public void mulaiPerbaikan(int N){
        mulai = new java.util.Date();
        System.out.println(new Timestamp(mulai.getTime()));
        String query = "";
        //query mulai perbaikan alat
        //query = "INSERT INTO perbaikan (id_alat,tanggal_masuk,tanggal_keluar) values ("N","NOW()","NULL") ON DUPLICATE KEY UPDATE id=“N”"
        db.connect(path);
        db.closeDatabase();
    }
    public void selesaiPerbaikan(int N){
        selesai= new java.util.Date();
        System.out.println(new Timestamp(selesai.getTime()));
        String query = "";
        //query selesai perbaikan alat
        //query = "INSERT INTO perbaikan (id_alat,tanggal_masuk,tanggal_keluar) values ("N","NOW()","") ON DUPLICATE KEY UPDATE id=“N”"
        db.connect(path);
        db.closeDatabase();
    }
    public static void main(String [] args){

    }
}
