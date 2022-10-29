package domain.validators;

import domain.User;

public class UserValidator implements  IValidator<User> {
    @Override
    public void validate(User entity) throws ValidationException {
        if(entity==null) {
            throw new ValidationException("User must not be null");
        }
        new NameValidator().validate(entity.getFirstName());
        new NameValidator().validate(entity.getLastName());

        if(entity.getAge()<14) {
            throw new ValidationException("User too young for this platform");
        }
        if(entity.getAge()>100) {
            throw new ValidationException("Is this user Iliescu?");
        }

        new EmailValidator().validate(entity.getEmail());
    }
}
