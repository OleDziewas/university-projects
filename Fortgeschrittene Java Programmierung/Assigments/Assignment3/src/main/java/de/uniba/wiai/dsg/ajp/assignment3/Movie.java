package de.uniba.wiai.dsg.ajp.assignment3;

/**
 * The class Movie represents the real-life concept of a movie, holding data related to existing movies
 * 'Movie' has several fields in order for its objects to be worked on under the framework of renting movies
 * Fields:
 *     PriceCodes: an enum used in order to categorize and prize movies according to their PriceCode
 *     PriceCodes resemble a real-life concept like a 'children's' movie
 *     Resolution: abstracts the real-life concept of screen resolution in order to determine price,
 *     needed for categorizing the field 'resolution'
 *     price: object with an abstract type, used in order to calculate the price. Its concrete class
 *     depends on the value of 'PriceCodes'
 *     resolution: used to store values specified by the enum 'Resolution'
 *     title: holds the title of a movie
 */
public class Movie {

	public enum PriceCodes {CHILDRENS, REGULAR, NEW_RELEASE, LOW_BUDGET}
	public enum Resolution {ULTRA_HD, HD}
	private Price price;
	private Resolution resolution;
	private String title;

	public Movie(String title, PriceCodes priceCode, Resolution resolution) {

		if(!checkStringParameter(title)) throw new IllegalArgumentException("The given title is empty or containing invalid characters!");
		if(!checkPriceCode(priceCode)) throw new IllegalArgumentException("The given price code is invalid!");
		if(!checkResolutionCode(resolution)) throw new IllegalArgumentException("The given resolution is invalid!");

		this.title = title;
		this.setPriceCode(priceCode);
		this.resolution = resolution;
	}

	public Resolution getResolution() {
		return resolution;
	}

	public void setResolution(Resolution resolution) {
		this.resolution = resolution;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Calculates the amount of money that has to be paid per rented movie for a given amount of days.
	 * If the Resolution of the rented movie is Ultra-HD (4K), the price will be charged with two units extra.
	 *
	 * @param daysRented the amount of days where the movie was rented
	 *
	 * @return the amount of money that has to be paid for a rented movie
	 */
	double getCharge(int daysRented) {
		if(!checkIntParameter(daysRented)) throw new IllegalArgumentException("The amount of days the movie was rented can't be negative or zero!");

		if (this.resolution == Resolution.ULTRA_HD){
			return price.getCharge(daysRented) + 2.0;
		}
		return price.getCharge(daysRented);
	}

	public PriceCodes getPriceCode() {
		return price.getPriceCode();
	}

	/**
	 * Sets a price category for the movie: Regular / Children / New Release / Low Budget.
	 *
	 * @param priceCode the given price code of type enum represents the category of the
	 *                  relative movies' price group
	 *
	 * @throws IllegalArgumentException if the given price code is not included in the enum
	 * 									of PriceCodes
	 */
	public void setPriceCode(PriceCodes priceCode) {


		if(!checkPriceCode(priceCode)) throw new IllegalArgumentException();

		switch (priceCode) {
			case REGULAR:
				price = new RegularPrice();
				break;
			case CHILDRENS:
				price = new ChildrensPrice();
				break;
			case NEW_RELEASE:
				price = new NewReleasePrice();
				break;
			case LOW_BUDGET:
				price = new LowBudgetPrice();
				break;
			default:
				throw new IllegalArgumentException("Incorrect Price Code");
		}
	}

	public int getFrequentRenterPoints(int daysRented) {
		if(!checkIntParameter(daysRented)) throw new IllegalArgumentException("The amount of days the movie is rented can't be zero or negative!");
		return price.getFrequentRenterPoints(daysRented);
	}

	private boolean checkStringParameter(String string){
		if(string == null || string.isEmpty() || string.isBlank()){
			return false;
		}else{
			return true;
		}
	}

	private boolean checkIntParameter(int integer){
		if(integer < 1){
			return false;
		}else{
			return true;
		}
	}

	private boolean checkPriceCode(PriceCodes priceCode){
		if(priceCode != null){
			for(PriceCodes pc: PriceCodes.values()){
				if(pc.equals(priceCode)){
					return true;
				}
			}
		}
		return false;
	}

	private boolean checkResolutionCode(Resolution resolution){
		if(resolution != null){
			for(Resolution rs: Resolution.values()){
				if(rs.equals(resolution)){
					return true;
				}
			}
		}
		return false;
	}
}