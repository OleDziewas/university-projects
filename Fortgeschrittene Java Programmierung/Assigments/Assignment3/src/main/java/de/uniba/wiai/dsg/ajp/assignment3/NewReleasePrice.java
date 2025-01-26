package de.uniba.wiai.dsg.ajp.assignment3;

public class NewReleasePrice extends Price{

	@Override
	double getCharge(int daysRented) throws IllegalArgumentException{
		if (daysRented <= 0){
			System.err.println("Incorrect value for days rented!");
			throw new IllegalArgumentException("Incorrect value for days rented!");
		}
		return daysRented * 3;
	}

	@Override
	int getFrequentRenterPoints(int daysRented) {
		if(daysRented <= 0){
			System.err.println("Incorrect value for days rented!");
			return 0;
		}
		if(daysRented > 1) {
			return 2;
		} else {
			return 1;
		}
	}

	@Override
	Movie.PriceCodes getPriceCode() {
		return Movie.PriceCodes.NEW_RELEASE;
	}
	
}
