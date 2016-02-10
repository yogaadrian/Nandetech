/**
 * Created by yoga on 2/10/2016.
 */
public class DriverStatistik {

    public static void main(String[] args){
        Statistik stat= new Statistik();
        stat.ShowStatistikPerbaikanAlat(0);
        System.out.println();
        stat.ShowStatistikPenggunaanAlat("proyektor");
        System.out.println();
        stat.ShowStatistikPenggunaanKelompok("mahasiswa");
    }
}
