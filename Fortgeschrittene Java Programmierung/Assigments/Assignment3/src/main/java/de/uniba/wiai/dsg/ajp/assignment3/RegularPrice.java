package de.uniba.wiai.dsg.ajp.assignment3;

public class RegularPrice extends Price {

	@Override
	double getCharge(int daysRented) throws IllegalArgumentException{
		if (daysRented <= 0){
			System.err.println("Incorrect value for days rented!");
			throw new IllegalArgumentException("Incorrect value for days rented!");
		}
		double result = 2;
		if (daysRented > 2) {
			result += (daysRented - 2) * 1.5;
		}
		return result;
	}

	@Override
	Movie.PriceCodes getPriceCode() {
		return Movie.PriceCodes.REGULAR;
	}

}
