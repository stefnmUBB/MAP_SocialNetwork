package domain.validators;

import java.util.regex.Pattern;

public class EmailValidator implements  IValidator<String> {
    @Override
    public void validate(String entity) throws ValidationException {
        if(entity==null){
            throw new ValidationException("Email address must not be null");
        }
        if(!Pattern.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", entity)) {
            throw new ValidationException("Incorrect email address");
        }
    }
}
