package user;

import database.Database;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by acel on 08-Feb-16.
 */
public class User {
    private final String path = "jdbc:mysql://localhost:3306/nandetech";
    private String id_user;
    private String nama_user;
    private String golongan;

    public User(String uid){
        id_user = uid;
    }

    public User(){
        id_user = "";
        nama_user = "";
        golongan = "";
    }

    public boolean exists(){
        Database db = new Database();
        ResultSet rs;
        boolean retval = false;
        final String query = "SELECT * " +
                "FROM user " +
                "WHERE id_user = '" + id_user + "'";
        db.connect(path);
        rs = db.fetchData(query);

        try {
            rs.last();
            if(rs.getRow() == 1)
                retval = true;
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        db.closeDatabase();

        return retval;
    }

    public void getData(){
        Database db = new Database();
        ResultSet rs;
        final String query = "SELECT * " +
                "FROM user " +
                "WHERE id_user = '" + id_user + "'";
        db.connect(path);
        rs = db.fetchData(query);

        try {
            rs.next();
            nama_user = rs.getString("nama_user");
            golongan = rs.getString("golongan");
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        db.closeDatabase();
    }
}
