import java.util.Scanner;

public class ComputeArea {
	public static void main(String[] args) {
		// creating a scanner object
		Scanner input = new Scanner(System.in);

		// ask user for radius
		System.out.print("Enter a number for radius: ");
		double radius = input.nextDouble();

		// compute area
		double area = radius * radius * 3.14159;

		// display results
		System.out.println("The area for the circle of radius " + radius + " is " + area);
	}
}