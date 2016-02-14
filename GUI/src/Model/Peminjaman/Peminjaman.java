package Model.Peminjaman;

import Model.Database.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by acel on 08-Feb-16.
 */
public class Peminjaman {
    private final String path = "jdbc:mysql://localhost:3306/nandetech";
    private Database db;

    public Peminjaman(){
        db = new Database();
    }

    public void tampilkanPeminjaman(int N, int pilihan){
        String query = "";
        ResultSet rs;
        ArrayList<ArrayList<String>> list = new ArrayList<>(1);

        switch(pilihan){
            case 1:
                //ID Peminjaman
                query = "SELECT * " +
                        "FROM peminjaman " +
                        "WHERE id_peminjaman = " + N;
                break;
            case 2:
                //ID User
                query = "SELECT * " +
                        "FROM peminjaman " +
                        "WHERE id_user = " + N;
                break;
            case 3:
                //ID Alat
                query = "SELECT * " +
                        "FROM peminjaman " +
                        "INNER JOIN peminjaman_alat ON peminjaman.id_peminjaman = peminjaman_alat.id_peminjaman " +
                        "WHERE id_alat = " + N;
                break;
        }

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

    public void cancelPeminjaman(int id_peminjaman){
        Database db = new Database();
        db.connect(path);

        final String query = "DELETE FROM Peminjaman " +
                "WHERE id_peminjaman = " + id_peminjaman;
        db.changeData(query);

        db.closeDatabase();
    }

    public void addPeminjaman(String id_user, String tanggal_peminjaman,
                              String tanggal_pengembalian, String deskripsi) {
        //Timestamp format: 2011-06-08 16:20:12
        Database db = new Database();
        db.connect(path);

        User peminjam = new User(id_user);
        if(peminjam.exists()){
            final String query = "INSERT INTO Peminjaman " +
                    "VALUES (NULL, '" + id_user + "', '" + tanggal_peminjaman + "', '" + tanggal_pengembalian +
                    "')";
            db.changeData(query);
        }
        final String query = "";

        db.closeDatabase();
    }

}
