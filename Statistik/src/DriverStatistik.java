import java.util.Scanner;

/**
 * Created by yoga on 2/10/2016.
 */
public class DriverStatistik {

    public static void main(String[] args){
        Statistik stat= new Statistik();

        Scanner sc = new Scanner(System.in);

        System.out.println("1. Tampilkan Statistik penggunaan nama alat");
        System.out.println("2. Tampilkan Statistik penggunaan oleh kelompok");
        System.out.println("3. Tampilkan Statistik perbaikan alat");
        System.out.print("Masukkan pilihan: ");
        int pil = sc.nextInt();
        switch(pil) {
            case 1:

                System.out.print("Masukkan nama alat: ");
                String namaalat = sc.next();

                stat.ShowStatistikPenggunaanAlat(namaalat);
                break;
            case 2:
                System.out.print("Masukkan nama kelompok: ");
                String golongan = sc.next();

                stat.ShowStatistikPenggunaanKelompok(golongan);
                break;
            case 3:
                System.out.print("Masukkan id alat: ");
                int idalat = sc.nextInt();

                stat.ShowStatistikPerbaikanAlat(idalat);
                break;
        }
    }
}
