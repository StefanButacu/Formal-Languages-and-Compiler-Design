import java.util.Scanner;


public class Circle {

    public static void main(String[] args) {
        double PI = 3.14;
        double radius;
        double perimeter;
        double aria;
        Scanner sc = new Scanner(System.in);
        System.out.print("Radius=");
        radius = sc.nextDouble();
        perimeter = 2 * PI * radius;
        System.out.print("\n");
        System.out.print("Perimeter=");
        System.out.print(perimeter);
        aria = PI * radius * radius;
        System.out.print("Aria=");
        System.out.print(aria);
    }

}
