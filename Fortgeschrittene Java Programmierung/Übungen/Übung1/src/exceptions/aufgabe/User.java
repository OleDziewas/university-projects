package exceptions.aufgabe;

public class User {

	private int age;
	private String email;

	public User(String email, int age) {
		super();
		this.email = email;
		this.age = age;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Validates the user fields
	 * 
	 * @throws UserException
	 *             if any validation error occured
	 */
	public void validate() throws UserException {
		// TODO validiere age und email mittels Validator
		// TODO kapsele die ValidationException bzw. ValidationRuntimeException
		ValidatorImpl val = new ValidatorImpl();
		try{
			val.validateAge(this.getAge());
			val.validateEmailWithRuntimeException(this.getEmail());
		}catch(ValidationException | ValidationRuntimeException e){
			throw new UserException();
		}
		// in einer UserException
	}

	@Override
	public String toString() {
		return "User [age=" + age + ", email=" + email + "]";
	}

}
