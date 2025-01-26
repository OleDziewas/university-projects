package exceptions.aufgabe;

public class ValidatorImpl implements Validator {

	@Override
	public void validateAge(int age) throws ValidationException {
		if (age <0 || age>120){
			throw new ValidationException();
		}
	}

	@Override
	public void validateEmailWithRuntimeException(String email){

		if (email == null || email.contains("@") == false){
			throw new ValidationRuntimeException();
		}
	}

}
