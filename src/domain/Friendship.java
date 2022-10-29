package domain;

import java.util.Arrays;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Friendship extends Entity<Long> {
    Long[] uids = new Long[2];
    public Friendship(Long userId1, Long userId2) {
        uids[0] = min(userId1, userId2);
        uids[1] = max(userId1, userId2);
    }

    /**
     * Gets users implied in a friendship
     * @return array of two friend users
     */
    public Long[] getUserIds() {
        return uids;
    }

    public boolean containsUser(Long id) {
        return uids[0].equals(id) || uids[1].equals(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Friendship that = (Friendship) o;
        return Arrays.equals(uids, that.uids);
    }

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

    public Long getTheOtherOne(Long id) {
        return id.equals(uids[0]) ? uids[1] : uids[0];
    }
}

