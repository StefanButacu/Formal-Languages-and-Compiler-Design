package ExamplesMLP;

import java.util.Scanner;

public class CMMDC {
    public static void main(String[] args) {
        int A;
        int B;
        Scanner scanner = new Scanner(System.in);
        System.out.print("A=");
        A = scanner.nextInt();
        System.out.print("B=");
        B = scanner.nextInt();
        while ( A != B){
            if( A > B)
                A -= B;
            else
                B -= A;
        }
        System.out.print("\n");
        System.out.print("ExamplesMLP.CMMDC=");
        System.out.print(A);
    }

}
