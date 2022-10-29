package domain.validators;

import java.util.Objects;
import java.util.regex.Pattern;

public class NameValidator implements IValidator<String> {

    @Override
    public void validate(String entity) throws ValidationException {
        if(entity==null) {
            throw new ValidationException("User name must not be null");
        }

        if(Objects.equals(entity, "")) {
            throw new ValidationException("Name must not be empty");
        }

        if(entity.matches(".*\\s\\s+.*")){
            throw new ValidationException("Multiple spaces detected");
        }

        String[] words = entity.split("\\s");
        for(String word : words) {
            if(!Pattern.matches("[A-Z](([a-z]+)|(\\.))", word)) {
                throw new ValidationException("Incorrect name : '" + word +"' in '"+ entity +"'");
            }
        }
    }
}
