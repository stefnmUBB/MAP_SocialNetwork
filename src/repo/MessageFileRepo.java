package repo;

import domain.Message;
import domain.User;
import domain.validators.IValidator;
import utils.Constants;

import java.time.LocalDateTime;
import java.util.List;

public class MessageFileRepo extends FileRepo<Long, Message>{

    /**
     * Creates new file repository instance
     *
     * @param fileName  file name
     * @param validator entity validator
     */
    public MessageFileRepo(String fileName, IValidator<Message> validator) {
        super(fileName, validator);
    }

    @Override
    public Message extractEntity(List<String> attributes) {
        Long id = Long.parseLong(attributes.get(0));
        Long authorID = Long.parseLong(attributes.get(1));
        String content = attributes.get(2);
        LocalDateTime date = LocalDateTime.parse(attributes.get(3), Constants.DATE_TIME_FORMATTER);
        Message message = new Message(authorID, content, date);
        message.setId(id);
        return message;
    }

    @Override
    public String entityAsString(Message entity) {
        String[] vals = new String[]
                {
                        entity.getId().toString(),
                        entity.getAuthorID().toString(),
                        entity.getContent(),
                        entity.getDateSent().format(Constants.DATE_TIME_FORMATTER)
                };
        return String.join(";", vals);
    }
}
