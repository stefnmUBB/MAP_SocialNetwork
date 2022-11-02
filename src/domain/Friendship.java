package domain;

import java.util.Arrays;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Friendship extends Entity<Long> {
    Long[] uids = new Long[2];

    /**
     * Creates a new friendship instance
     * @param userId1 one of the friend's Id
     * @param userId2 the other friend's Id
     */
    public Friendship(Long userId1, Long userId2) {
        uids[0] = min(userId1, userId2);
        uids[1] = max(userId1, userId2);
    }

    /**
     * Gets users implied in a friendship, sorted by their Ids
     * @return array of two friend users
     */
    public Long[] getUserIds() {
        return uids;
    }

    /**
     * Checks if a user is part of the friendship
     * @param id Id of the user to check
     * @return true if user represents one end of the friendship, false otherwise
     */
    public boolean containsUser(Long id) {
        return uids[0].equals(id) || uids[1].equals(id);
    }

    /**
     * Checks if a Friendship instance equals an object
     * @param o the object to check
     * In order for the object to be eligible for check, it must a non-null
     * friendship instance. It must also have the same ids as the instance it is compared to
     * @return true if the two instances are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Friendship that = (Friendship) o;
        return Arrays.equals(uids, that.uids);
    }

    /**
     * Gets a text representation of the friendship
     * @return String containing user-friendly friendship data
     */
    @Override
    public String toString() {
        return "Friendship{" +
                "ID=" + getId() +
                ", uids=" + Arrays.toString(uids) +
                '}';
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(uids);
    }

    /**
     * Knowing one of the friends, the other one is retrieved.
     * @param id The id of the known user implied in friendship
     * No checks are performed over the user id. An id of an user
     * that is not part of the friendship can be provided, in which case
     * the function returns any of the two actual friends. However, the function is guaranteed
     * to work when the user exists in a friendship
     * @return the other user in the friendship, or any of the friends if the user is not part of friendship
     */
    public Long getTheOtherOne(Long id) {
        return id.equals(uids[0]) ? uids[1] : uids[0];
    }
}

