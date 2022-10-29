package service;

import domain.Friendship;
import domain.User;
import repo.IRepo;
import reports.AbstractReport;
import reports.CombinedReport;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

public class UserService extends Service<Long, User> {
    IService<Long, Friendship> friendshipService;

    public UserService(IRepo<Long, User> repo, IService<Long, Friendship> fserv) {
        super(repo);
        friendshipService = fserv;
    }

    public AbstractReport remove(Long id) throws EntityIdNotFoundException {
        CombinedReport report = new CombinedReport();


        var col = (Collection<? extends Friendship>) friendshipService.getAll();
        for(Friendship f : new ArrayList<Friendship>(col)) { // clone to avoid ConcurrentModifficationException
            if(f.containsUser(id)) {
                report.add(friendshipService.remove(f.getId()));
            }
        }
        report.add(super.remove(id));
        return report;
    }

    public Long generateNewId() {
        return new Date().getTime();
    }
}
