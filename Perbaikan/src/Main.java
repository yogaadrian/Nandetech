import java.util.Scanner;

/**
 * Created by user on 09/02/2016.
 */
public class Main {
    public static void main(String [] args){
        Scanner sc = new Scanner(System.in);
        Perbaikan pb = new Perbaikan();
        int valid = 0;
        while(valid != 999) {
            int i = sc.nextInt();
            if(i==1){
                pb.tampilkanPerbaikan(1);
            }
            else if (i==2) {
                pb.mulaiPerbaikan(1);
            }
            else if (i==3){
                pb.selesaiPerbaikan(1);
            }
            else break;
        }
    }
}
