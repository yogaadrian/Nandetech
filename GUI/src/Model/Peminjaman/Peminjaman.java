package Model.Peminjaman;

import Model.Database.Database;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by acel on 08-Feb-16.
 */
public class Peminjaman {
    private final String path = "jdbc:mysql://localhost:3306/nandetech";
    private Database db;

    public Peminjaman(){
        db = new Database();
    }

    public ArrayList<ArrayList<String>> semuaAlat (){
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

    public ArrayList<ArrayList<String>> tampilkanPeminjaman(int N, int pilihan){
        String query = "";
        ResultSet rs;
        ArrayList<ArrayList<String>> list = new ArrayList<>(1);

        switch(pilihan){
            case 1:
                //ID Peminjaman
                query = "SELECT * " +
                        "FROM peminjaman " +
                        "JOIN peminjaman_alat " +
                        "WHERE peminjaman.id_peminjaman = " + N + " " +
                        "AND peminjaman.id_peminjaman = peminjaman_alat.id_peminjaman";
                break;
            case 2:
                //ID User
                query = "SELECT * " +
                        "FROM peminjaman " +
                        "JOIN peminjaman_alat " +
                        "WHERE peminjaman.id_user = " + N + " " +
                        "AND peminjaman.id_peminjaman = peminjaman_alat.id_peminjaman";
                break;
            case 3:
                //ID Alat
                query = "SELECT * " +
                        "FROM peminjaman " +
                        "JOIN peminjaman_alat " +
                        "WHERE id_alat = " + N + " " +
                        "AND peminjaman.id_peminjaman = peminjaman_alat.id_peminjaman";
            break;
        }

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

    public void cancelPeminjaman(int id_peminjaman){
        Database db = new Database();
        db.connect(path);

        String query = "DELETE FROM Peminjaman " +
                "WHERE id_peminjaman = " + id_peminjaman;
        db.changeData(query);

        db.closeDatabase();
    }

    public boolean cekPeminjaman(int id_alat, String tanggal_peminjaman, String tanggal_pengembalian){

        Timestamp pinjam = Timestamp.valueOf(tanggal_peminjaman);
        Timestamp kembali = Timestamp.valueOf(tanggal_pengembalian);

        boolean retval = true;

        Database db = new Database();
        db.connect(path);

        String query = "SELECT * " +
                "FROM peminjaman JOIN peminjaman_alat " +
                "WHERE id_alat = " + id_alat;
        ResultSet rs = db.fetchData(query);
        try {
            while(rs.next()){
                Timestamp cekPinjam = Timestamp.valueOf(rs.getString("tanggal_peminjaman"));
                Timestamp cekKembali= Timestamp.valueOf(rs.getString("tanggal_pengembalian"));
                if(pinjam.after(cekKembali) || kembali.before(cekPinjam)){
                    //do nothing
                } else {
                    retval = false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        db.closeDatabase();

        return retval;
    }

    public void addPeminjaman(int[] alat, String id_user, String tanggal_peminjaman,
                              String tanggal_pengembalian, String deskripsi) {
        //Timestamp format: 2011-06-08 16:20:12
        Database db = new Database();
        db.connect(path);

        User peminjam = new User(id_user);

        int id_peminjaman = 0;
        String query = "SELECT *" +
                "FROM peminjaman";
        ResultSet rs = db.fetchData(query);

        try {
            rs.last();
            id_peminjaman = rs.getRow() + 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        boolean valid = true;
        for(int i=0; i<alat.length; i++){
            valid = valid && cekPeminjaman(alat[i], tanggal_peminjaman, tanggal_pengembalian);
        }

        if(valid) {
            if (peminjam.exists()) {
                query = "INSERT INTO peminjaman " +
                        "VALUES (" + id_peminjaman + ", '" + id_user + "', '" + deskripsi + "', '" +
                        tanggal_peminjaman + "', '" + tanggal_pengembalian + "')";
                db.changeData(query);
            }

            for (int i = 0; i < alat.length; i++) {
                query = "INSERT INTO peminjaman_alat " +
                        "VALUES (" + id_peminjaman + ", " + alat[i] + ")";
                db.changeData(query);
            }
        } else {
            //tampilkan pesan kesalahan
        }
        db.closeDatabase();
    }
    /* Module testing */
    /*
    public static void main(String[] args){
        System.out.println("1. Tampilkan Peminjaman");
        System.out.println("2. Tambahkan Peminjaman");
        System.out.println("3. Batalkan Peminjaman");
        System.out.print("Masukkan pilihan: ");

        Peminjaman p = new Peminjaman();
        Scanner sc = new Scanner(System.in);

        int pil = sc.nextInt();
        switch(pil){
            case 1:
                //Tampilkan Peminjaman
                System.out.println("1. ID Peminjaman");
                System.out.println("2. ID User");
                System.out.println("3. ID Alat");
                System.out.print("Masukkan pilihan: ");
                int pil2 = sc.nextInt();
                System.out.print("Masukkan ID: ");
                int N = sc.nextInt();
                p.tampilkanPeminjaman(N, pil2);
                break;
            case 2:
                //Tambahkan Peminjaman
                sc.nextLine();
                System.out.print("ID Alat (a;b;c;): ");
                String row = sc.nextLine();
                String[] rows = row.split(";");
                int[] alat = new int[rows.length];
                for(int i=0; i<rows.length; i++){
                    alat[i] = Integer.parseInt(rows[i]);
                }
                System.out.print("ID user: ");
                String id = sc.next();
                sc.nextLine();
                System.out.print("Tanggal Peminjaman: ");
                String pm = sc.nextLine(); //Format: 2011-06-08 16:20:12
                System.out.print("Tanggal Pengembalian: ");
                String pn = sc.nextLine(); //Format: 2011-06-08 16:20:12
                System.out.print("Deskrispi: ");
                String dc = sc.nextLine();

                p.addPeminjaman(alat, id, pm, pn, dc);
                break;
            case 3:
                //Batalkan Peminjaman
                System.out.print("Masukkan ID Peminjaman: ");
                int id_pn = sc.nextInt();
                p.cancelPeminjaman(id_pn);
                break;
        }
    } */
}
