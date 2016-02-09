package Model;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by acel on 08-Feb-16.
 */
public class Database {
    private final String username = "root";
    private final String password = "";
    private Connection conn;

    public Database() {
        conn = null;
    }

    public void connect(String database) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(database, username, password);
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public ResultSet fetchData(String sql) {
        try {
            PreparedStatement dbStatement = conn.prepareStatement(sql);
            ResultSet rs = dbStatement.executeQuery();
            return rs;
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public String changeData(String sql) {
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
            return "Change success";
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return "Change failed";
        }
    }

    public void closeDatabase() {
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
