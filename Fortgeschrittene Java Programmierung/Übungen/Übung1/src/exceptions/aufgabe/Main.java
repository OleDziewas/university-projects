package exceptions.aufgabe;

public class Main {

	public static void main(String[] args) {
		validateCorrect(new User("mail@example.com", 100));
		validateIncorrect(new User(null, 100), "email is null");
		validateIncorrect(new User("mailexample.com", 100),
				"email does not contain @ sign");
		validateIncorrect(new User("mail@example.com", -1), "negative age");
		validateIncorrect(new User("mail@example.com", 9999999),
				"age too large");
	}

	private static void validateIncorrect(User user, String reason) {
		try {
			user.validate();
			System.err
					.format("UserException expected [reason: %s], but none was thrown. %n%s is invalid and should provoke UserException!%n",
							reason, user);
			System.exit(1);
		} catch (UserException e) {
			System.out
					.format("Expected UserException occurred for invalid %s [reason: %s] with exception message %s%n",
							user, reason, e);
		}
	}

	private static void validateCorrect(User user) {
		try {
			user.validate();
		} catch (UserException e) {
			System.err
					.format("UserException with message %s thrown for correct user.%nShould not happen, as %s is valid!%n",
							e, user);
			System.exit(1);
		}
	}

}
