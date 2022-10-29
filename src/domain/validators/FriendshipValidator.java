package domain.validators;

import domain.Friendship;
import domain.User;
import repo.IRepo;

public class FriendshipValidator implements IValidator<Friendship> {

    IRepo<Long, User> context;

    public FriendshipValidator(IRepo<Long, User> context) {
        this.context = context;
    }

    @Override
    public void validate(Friendship entity) throws ValidationException {
        Long id1 = entity.getUserIds()[0];
        Long id2 = entity.getUserIds()[1];
        if(id1.equals(id2)){
            throw new ValidationException("User can't be friends with themselves");
        }
        if(context.getById(id1)==null) {
            throw new ValidationException("Can't find user with id " + id1);
        }
        if(context.getById(id2)==null) {
            throw new ValidationException("Can't find user with id " + id2);
        }
    }
}
