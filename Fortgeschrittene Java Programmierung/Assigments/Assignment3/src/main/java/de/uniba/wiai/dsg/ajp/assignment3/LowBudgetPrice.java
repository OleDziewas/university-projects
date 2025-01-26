package de.uniba.wiai.dsg.ajp.assignment3;

public class LowBudgetPrice extends Price{
    @Override
    double getCharge(int daysRented) throws IllegalArgumentException{
        if (daysRented <= 0){
            System.err.println("Incorrect value for days rented!");
            throw new IllegalArgumentException("Incorrect value for days rented!");
        }
        double result = 1.5;
        if (daysRented==1){
            return 0.5;
        }
        if (daysRented > 1) {
            result += (daysRented - 2) * 0.5;
        }
        return result;
    }

    @Override
    Movie.PriceCodes getPriceCode() {
        return Movie.PriceCodes.LOW_BUDGET;
    }
}
