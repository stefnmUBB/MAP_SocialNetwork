package service;

import config.AppContext;
import domain.Friendship;
import domain.User;
import domain.validators.FriendshipValidator;
import domain.validators.UserValidator;
import repo.EntityAlreadyExistsException;
import repo.FriendshipFileRepo;
import repo.IRepo;
import repo.UserFileRepo;
import reports.AbstractReport;

import java.util.ArrayList;
import java.util.List;

public class Network {
    private IService<Long, User> userServ;
    private IService<Long, Friendship> friendshipServ;

    public Network(IService<Long, User> userServ, IService<Long, Friendship> friendshipServ) {
        this.userServ = userServ;
        this.friendshipServ = friendshipServ;
    }

    public AbstractReport addUser(User u) throws EntityAlreadyExistsException {return userServ.add(u);}
    public AbstractReport updateUser(User u) {return userServ.update(u);}
    public AbstractReport removeUser(Long uid) throws EntityIdNotFoundException { return userServ.remove(uid);}

    public AbstractReport addFriendship(Friendship f) throws EntityAlreadyExistsException {
        return friendshipServ.add(f);
    }
    public AbstractReport removeFriendship(Long fid) throws EntityIdNotFoundException { return friendshipServ.remove(fid);}

    public static Network loadDefaultNetwork() {
        UserFileRepo usersRepo =
                new UserFileRepo(AppContext.USERS_FILE_NAME, new UserValidator());
        FriendshipFileRepo friendshipsRepo =
                new FriendshipFileRepo(AppContext.FRIENDSHIPS_FILE_NAME, new FriendshipValidator(usersRepo));
        FriendshipService friendshipsServ = new FriendshipService(friendshipsRepo);
        UserService usersServ = new UserService(usersRepo, friendshipsServ);
        return new Network(usersServ, friendshipsServ);
    }

    public Iterable<User> getAllUsers() {
        return userServ.getAll();
    }

    public Iterable<Friendship> getAllFriendships() {
        return friendshipServ.getAll();
    }

    public List<Community> getCommunities() {
        List<Community> result = new ArrayList<>();

        for(var user : getAllUsers()){
            boolean alreadyIn = false;
            for(var com : result) {
                if(com.contains(user.getId())) {
                    alreadyIn = true;
                    break;
                }
            }
            //if(alreadyIn) continue;
            if(!alreadyIn) {
                //System.out.println(user.getId());
                Community community = new Community(this, user.getId());
                result.add(community);
            }
        }
        return result;
    }

    public int communitiesCount() {
        return getCommunities().size();
    }
}
