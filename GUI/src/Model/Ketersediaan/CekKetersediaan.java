package Model.Ketersediaan;

import Model.Database.Database;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * Created by Julio on 2/9/2016.
 */
public class CekKetersediaan {
    private final String path = "jdbc:mysql://localhost:3306/nandetech";
    private Database db;

    public CekKetersediaan(){
        db = new Database();
    }

    public ArrayList<ResultRow> removeDuplicate(ArrayList<ResultRow> ArRow){
        ArrayList<ResultRow> arTemp = new ArrayList<>();
        for (int i=0;i<ArRow.size();i++) {
            ResultRow rowNewest = ArRow.get(i);
            for (int j=0;j<ArRow.size();j++) {
                if ((i!=j)&&(rowNewest.getId_alat()==ArRow.get(j).getId_alat())){
                    if (ArRow.get(j).getTanggal_peminjaman().after(rowNewest.getTanggal_peminjaman())){
                        rowNewest=ArRow.get(j);
                        ArRow.remove(j);
                    }
                }
            }
            arTemp.add(rowNewest);
        }
        return arTemp;
    }
    public ArrayList<ResultRow> cek(String namaAlat, Timestamp tanggalPinjam){
        ResultSet rs;
        ArrayList<ResultRow> ArRow = new ArrayList<>();
        String query = "SELECT alat.nama_alat, alat.id_alat, peminjaman.tanggal_peminjaman, alat.kondisi, peminjaman.tanggal_pengembalian FROM peminjaman RIGHT OUTER JOIN peminjaman_alat ON peminjaman.id_peminjaman=peminjaman_alat.id_peminjaman RIGHT OUTER JOIN alat ON alat.id_alat=peminjaman_alat.id_alat WHERE LCASE(alat.nama_alat)=LCASE(\""+namaAlat+"\")";
        db.connect(path);
        rs = db.fetchData(query);
        try{
            if (rs.next()){
                do{
                    ResultRow row = new ResultRow(rs.getInt("alat.id_alat"),rs.getTimestamp("tanggal_peminjaman"),rs.getTimestamp("tanggal_pengembalian"),rs.getString("nama_alat"),rs.getString("kondisi"));
                    ArRow.add(row);
                } while(rs.next());
                ArRow = removeDuplicate(ArRow);
                for (int i=0;i<ArRow.size();i++){
                    if (ArRow.get(i).getKondisi().equalsIgnoreCase("Rusak")){
                        ArRow.get(i).setAvailability("Not Available");
                    } else {
                        if (ArRow.get(i).getTanggal_pengembalian()!=null) {
                            if ((ArRow.get(i).getTanggal_pengembalian().after(tanggalPinjam))&&(ArRow.get(i).getTanggal_peminjaman().before(tanggalPinjam))||(ArRow.get(i).getTanggal_peminjaman().equals(tanggalPinjam))||(ArRow.get(i).getTanggal_pengembalian().equals(tanggalPinjam))) {
                                ArRow.get(i).setAvailability("Not Available");
                            } else {
                                ArRow.get(i).setAvailability("Available");
                            }
                        } else {
                            ArRow.get(i).setAvailability("Available");
                        }
                    }
                }
                for (int i=0;i<ArRow.size();i++){
                    System.out.println("ID Alat : "+ArRow.get(i).getId_alat()+" Nama : "+ArRow.get(i).getNama_alat()+" Status : "+ArRow.get(i).getAvailability());
                }
            } else {
                //EMPTY SET
                System.out.println("EMPTY!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ArRow;
    }
}
