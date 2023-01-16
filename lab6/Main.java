import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int x;
        x = sc.nextInt();
        int y;
        y = 3;
        int z;
        z = x + y * 2;
        System.out.println(z);
        int a;
        a = x + y ;
        System.out.println(a);

        a = x - y;
        System.out.println(a);
        a = x / y;
        System.out.println(a);

        a = x + y - x;
        System.out.println(a);

    }


}