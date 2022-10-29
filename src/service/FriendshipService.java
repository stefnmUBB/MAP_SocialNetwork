package service;

import domain.Friendship;
import domain.User;
import repo.EntityAlreadyExistsException;
import repo.IRepo;
import reports.AbstractReport;
import reports.CombinedReport;

import java.util.Date;
import java.util.function.Predicate;

public class FriendshipService extends Service<Long, Friendship> {

    public FriendshipService(IRepo<Long, Friendship> frepo) {
        super(frepo);
    }

    public AbstractReport add(Friendship f) throws EntityAlreadyExistsException {
        if(exists(friendship ->
                friendship.containsUser(f.getUserIds()[0])
             && friendship.containsUser(f.getUserIds()[1]))
        )
            throw new EntityAlreadyExistsException();
        return super.add(f);
    }


    public Long generateNewId() {
        return new Date().getTime();
    }
}
