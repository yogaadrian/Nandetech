import alat.Peminjaman;

import java.util.Scanner;

/**
 * Created by acel on 08-Feb-16.
 */
public class Main {
    public static void displayMenu(){
        System.out.println("Pilihan Menu");
        System.out.println("1. Cek Ketersediaan Alat");
        System.out.println("2. Peminjaman Alat");
        System.out.println("3. Perbaikan Alat");
        System.out.println("4. Statistik Alat");
        System.out.println("5. About");
        System.out.println("6. Keluar");
        System.out.print("Masukkan pilihan: ");
    }

    public static void prosesMenu(int pilihan){
        switch(pilihan){
            case 1:
                //Cek Ketersediaan Alat
                break;
            case 2:
                //Peminjaman Alat
                Peminjaman p = new Peminjaman();
                Scanner sc = new Scanner(System.in);
                System.out.println("1. ID Peminjaman");
                System.out.println("2. ID User");
                System.out.println("3. ID Alat");
                System.out.print("Masukkan pilihan: ");
                int pil = sc.nextInt();
                System.out.print("Masukkan ID: ");
                int N = sc.nextInt();
                p.tampilkanPeminjaman(N, pil);
                break;
            case 3:
                //Perbaikan Alat
                break;
            case 4:
                //Statistik Alat
                break;
            case 5:
                //About
                break;
            case 6:
                //Keluar
                break;
            default:
                //Pilihan tidak sesuai
                break;
        }
    }

    public static void main(String[] args) {
        //launch(args);
        int pilihan = 0;
        while(pilihan != 6){
            displayMenu();
            Scanner sc = new Scanner(System.in);
            pilihan = sc.nextInt();
            prosesMenu(pilihan);
        }
    }
}
