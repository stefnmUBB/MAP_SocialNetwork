package domain.validators;

import domain.Message;
import domain.User;

public class MessageValidator implements IValidator<Message> {
    @Override
    public void validate(Message entity) throws ValidationException {
        String content = entity.getContent();
        if(content==null) {
            throw new ValidationException("Message content cannot be null");
        }
        if(content.length()<=3) {
            throw new ValidationException("Message content too short");
        }
        if(!content.matches("^[a-zA-Z0-9.\\-! ]*$")){
            throw new ValidationException("Invalid characters detected in message content");
        }
    }
}
