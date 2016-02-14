package Model.Ketersediaan;

import java.sql.Timestamp;

/**
 * Created by Julio Savigny on 2/9/2016.
 */
//Class DRIVER

public class Main {
    public static void main(String[] args){
        CekKetersediaan driverCoba = new CekKetersediaan();
        Timestamp sekarang = new Timestamp(new java.util.Date().getTime());
        driverCoba.cek("APa ya",sekarang);
    }
}
