package profiling.fibonacci;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	
	public static void main(String[] args) throws IOException {
		FibonacciCalculator calc = new FibonacciCalculator("result.txt");
		
		System.out.println("Please enter the index until which the Fibonacci Sequence should be calculated.");
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		int index = -1;
		while(index <= 0){
			System.out.println("The index must be a positive number. Please enter:");
			try {
				index = Integer.parseInt(reader.readLine());
			} catch (NumberFormatException | IOException e) {
			}
			
		}
		
		for(int i = 1; i<= index; i++){
			calc.calculateFibonacciNumber(i);
		}
		System.out.println("Calulation finished");
		
	}
	
}
