package de.uniba.wiai.dsg.ajp.assignment3;

public class Rental {

	private int daysRented;
	private Movie movie;
	private double discount = 0.0;

	public void setDiscount(double discount) {
		try {
			validateDiscount(discount);
			this.discount = discount;
		}catch(IllegalArgumentException e){
			throw e;
		}
	}

	public double getDiscount() {
		return discount;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		try {
			validateMovie(movie);
			this.movie = movie;
		}catch(IllegalArgumentException e){
			throw e;
		}
	}

	public int getDaysRented() {
		return daysRented;
	}

	public void setDaysRented(int daysRented) {
		try{
			validateDaysRented(daysRented);
			this.daysRented = daysRented;
		}catch(IllegalArgumentException e){
			throw e;
		}
	}

	public double getCharge() {
		return (movie.getCharge(daysRented) * (1.0-this.discount));
	}

	public int getFrequentRenterPoints() {
		return movie.getFrequentRenterPoints(daysRented);
	}

	private void validateDiscount(double discount) {
		if(discount < 0 || discount >1) {
			throw new IllegalArgumentException("The discount is negative or null");
		}
	}

	private void validateMovie(Movie movie){
		try {
			if (movie.getPriceCode() == (null)) {
				throw new IllegalArgumentException("The price code must not be negative");
			}
		}
		catch (NullPointerException e) {
			// in case the if-clause tries to access a non-instantiated price
			throw new IllegalArgumentException("There is no price associated with the movie");
		}

		if(movie.getResolution() != null) {
			boolean isValidResolution = false;
			for (Movie.Resolution rs : Movie.Resolution.values()) {
				if (!rs.equals(movie.getResolution())) {
					isValidResolution = true;
				}
			}
			if (isValidResolution == false) {
				throw new IllegalArgumentException("The value of the resolution of the movie is invalid");
			}
		}
		else {throw new IllegalArgumentException("The resolution of the movie is null");}

		if(movie.getTitle() == null || movie.getTitle().isEmpty() || movie.getTitle().isBlank()) {
			throw new IllegalArgumentException("The title of the movie is null or empty");
		}

	}

	private void validateDaysRented(int daysRented) {
		if (daysRented <= 0) {
			throw new IllegalArgumentException("The days rented is negative, zero or null");
		}
	}

}
