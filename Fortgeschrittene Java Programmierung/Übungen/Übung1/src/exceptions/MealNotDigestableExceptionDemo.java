package exceptions;

public class MealNotDigestableExceptionDemo {

	public void digest(String meal) throws MealNotDigestableException {

		if ("Musaka".equals(meal)) {

			MealNotDigestableException up = new MealNotDigestableException(meal
					+ " not digestable");
			throw up;
		}
	}
	
	public static void main(String[] args) throws MealNotDigestableException {
		MealNotDigestableExceptionDemo demo = new MealNotDigestableExceptionDemo();

		String firstMeal = "Schnitzel mit Pommes";
		demo.digest(firstMeal);

		String secondMeal = "Musaka";
		demo.digest(secondMeal);
	}

}
