package de.uniba.wiai.dsg.ajp.assignment3;

public class ChildrensPrice extends Price {

	@Override
	double getCharge(int daysRented) throws IllegalArgumentException {
		if (daysRented <= 0){
			System.err.println("Incorrect value for days rented!");
			throw new IllegalArgumentException("Incorrect value for days rented!");
		}
		double result = 1.5;
		if (daysRented > 3) {
			result += (daysRented - 3) * 1.5;
		}
		return result;
	}
	@Override
	int getFrequentRenterPoints(int daysRented) {
		if(daysRented <= 0){
			System.err.println("Incorrect value for days rented!");
		}
		return 0;
	}

	@Override
	Movie.PriceCodes getPriceCode() {
		return Movie.PriceCodes.CHILDRENS;
	}

}
