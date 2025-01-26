package de.uniba.wiai.dsg.ajp.assignment3;

import java.util.LinkedList;
import java.util.List;

/**
 * The class Customer is an abstraction of a real-life customer,
 * reducing his attributes to his name and a List of his Rentals.
 * The task of the class is to summarize costs and frequent total renter points for the customer.
 * Fields
 * 		name: a String containing the name of the customer, used for a personalized statement
 * 		rentals: a LinkedList of Rentals holding information about the rentals of the customer
 */
public class Customer {

	private String name;

	private List<Rental> rentals = new LinkedList<Rental>();

	public Customer(String name) throws IllegalArgumentException{
		super();
		if ((name == null) || (name.isEmpty())){
			throw new IllegalArgumentException(("Name can not be empty or null."));
		}

		this.name = name;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Rental> getRentals() {
		return rentals;
	}

	public void setRentals(List<Rental> rentals) {
		this.rentals = rentals;
	}

	/**
	 * Concatenates String containing detailed information about the rentals
	 * as well as a summarized overview about total costs
	 * and frequent total renter points after calculating them.
	 *
	 * @return A String containing the information mentioned above.
	 */
	public String statement() {
		String result = "Rental Record for " + getName() + "\n";

		int frequentRenterPoints = 0;
		for (Rental each : this.rentals) {
			frequentRenterPoints += each.getFrequentRenterPoints();

			// show figures for this rental
			result += "\t" + each.getMovie().getTitle() +" " + each.getMovie().getResolution().toString()+ "\t"
					+ String.valueOf(each.getCharge())+ " Discount: "+each.getDiscount()*100 + "%\n";
		}

		// add footer lines
		result += "Amount owed is " + String.valueOf(getTotalCharge()) + "\n";
		result += "You earned " + String.valueOf(frequentRenterPoints)
				+ " frequent renter points";
		return result;
	}
	/**
	 * Concatenates String containing detailed information about the rentals
	 * as well as a summarized overview about total costs
	 * and frequent total renter points after calculating them.
	 * HTML-Tags are added to the String.
	 *
	 * @return A String containing the information mentioned above.
	 */
	public String htmlStatement() {
		String result = "<H1>Rentals for <EM>" + getName() + "</EM></H1><P>\n";

		for (Rental each : rentals) {
			// show figures for each rental
			result += each.getMovie().getTitle()+ " " + each.getMovie().getResolution().toString() + ": "
					+ String.valueOf(each.getCharge()) + " Discount: "+each.getDiscount()*100 + "%<BR>\n";
		}

		// add footer lines
		result += "<P>You owe <EM>" + String.valueOf(getTotalCharge())
				+ "</EM><P>\n";
		result += "On this rental you earned <EM>"
				+ String.valueOf(getTotalFrequentRenterPoints())
				+ "</EM> frequent renter points<P>";
		return result;
	}

	/**
	 * Calculates the total amount of money the customer has to pay for all his rented movies.
	 * @return Total amount of money that will be charged from the customer.
	 */
	double getTotalCharge() {
		double result = 0;

		for (Rental each : rentals) {
			result += each.getCharge();
		}

		return result;
	}

	/**
	 * Calculates a bonus for the customer. This is done based on the number of days the customer has rented a movie.
	 * @return The number of total points attributed to the customer.
	 */
	int getTotalFrequentRenterPoints() {
		int result = 0;

		for (Rental each : rentals) {
			result += each.getFrequentRenterPoints();
		}

		return result;
	}

}
