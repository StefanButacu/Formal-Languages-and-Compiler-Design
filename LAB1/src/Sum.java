import java.util.Scanner;

public class Sum {
    public static void main(String[] args) {
        int n;
        Scanner sc = new Scanner(System.in);
        System.out.print("n=");
        n = sc.nextInt();
        int i = 0;
        int sum = 0;
        int x;
        while (i < n){
            x = sc.nextInt();
            sum += x;
            i += 1;
        }
        System.out.print("\n");
        System.out.print("sum=");
        System.out.print(sum);


    }
}
