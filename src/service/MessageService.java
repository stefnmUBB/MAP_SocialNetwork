package service;

import domain.Message;
import repo.IRepo;

import java.util.Date;

public class MessageService extends Service<Long, Message>{

    /**
     * Creates new service
     *
     * @param repo repo to handle
     */
    public MessageService(IRepo<Long, Message> repo) {
        super(repo);
    }

    @Override
    public Long generateNewId() {
        return new Date().getTime();
    }
}
