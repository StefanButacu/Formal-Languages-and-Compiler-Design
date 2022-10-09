import java.util.Scanner;

public class Circle {

    public static void main(String[] args) {
        Double PI = 3.14;
        Double radius;
//        double Double;
        Double perimeter;
        Double aria;
        Scanner sc = new Scanner(System.in);
        System.out.print("Radius=");
        radius = sc.nextDouble();
        perimeter = 2 * PI * radius;
        System.out.print("Perimeter=");
        System.out.print(perimeter);
        aria = PI * radius * radius;
        System.out.print("Aria=");
        System.out.print(aria);
    }

}
