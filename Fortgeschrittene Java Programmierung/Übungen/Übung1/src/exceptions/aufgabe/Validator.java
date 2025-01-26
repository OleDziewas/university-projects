package exceptions.aufgabe;

public interface Validator {

	/**
	 * Validates the age.
	 * 
	 * @param age
	 *            the age of a person
	 * @throws ValidationException
	 *             if age is not between 0 and 120
	 */
	void validateAge(int age) throws ValidationException;

	/**
	 * Validates the email address.
	 * 
	 * @param email
	 *            the email address
	 * @throws ValidationRuntimeException
	 *             if email is null or does not contain the @ sign
	 */
	void validateEmailWithRuntimeException(String email);

}
