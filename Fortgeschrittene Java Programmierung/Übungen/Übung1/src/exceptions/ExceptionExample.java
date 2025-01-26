package exceptions;

public class ExceptionExample {

	private static void divByZero() {
		int result = 10 / 0; // line 5
	}

	public static void main(String[] args) {
		System.out.println("Before");
		divByZero(); // line 11
		System.out.println("After");
	}

}
