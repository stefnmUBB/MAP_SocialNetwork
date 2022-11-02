package repo;

import domain.Friendship;
import domain.validators.IValidator;

import java.util.List;

public class FriendshipFileRepo extends FileRepo<Long, Friendship> {
    /**
     * Creates new file repo for friendship
     * @param fileName friendships file name
     * @param validator friendship validator
     */
    public FriendshipFileRepo(String fileName, IValidator<Friendship> validator) {
        super(fileName, validator);
    }

    @Override
    public Friendship extractEntity(List<String> attributes) {
        Long id = Long.parseLong(attributes.get(0));
        Long uid1 = Long.parseLong(attributes.get(1));
        Long uid2 = Long.parseLong(attributes.get(2));
        Friendship f = new Friendship(uid1, uid2);
        f.setId(id);
        return f;
    }

    @Override
    public String entityAsString(Friendship entity) {
        String[] vals = new String[]
        {
                entity.getId().toString(),
                entity.getUserIds()[0].toString(),
                entity.getUserIds()[1].toString(),
        };
        return String.join(";", vals);
    }
}
