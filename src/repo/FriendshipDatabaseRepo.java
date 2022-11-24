package repo;

import domain.Friendship;
import domain.User;
import domain.validators.IValidator;
import utils.Constants;

import java.sql.*;
import java.time.LocalDateTime;

public class FriendshipDatabaseRepo extends AbstractDatabaseRepo<Long, Friendship> {

    public FriendshipDatabaseRepo(IValidator<Friendship> validator) {
        super(validator);
    }

    public FriendshipDatabaseRepo(String url, String username, String password, IValidator<Friendship> validator) {
        super(url, username, password, validator);
    }
    @Override
    protected PreparedStatement getSelectQuery(Connection conn) throws SQLException {
        String sql = "Select * FROM public.\"Friendship\" ORDER BY id ASC";
        return conn.prepareStatement(sql);
    }

    @Override
    protected PreparedStatement getInsertQuery(Connection conn, Friendship friendship) throws SQLException {
        String[] generatedColumns = { "id" };
        String sql = "INSERT INTO public.\"Friendship\" (\"uid1\", \"uid2\", \"friendsFrom\") VALUES (?,?,?)";
        PreparedStatement ps = conn.prepareStatement(sql, generatedColumns);
        ps.setLong(1, friendship.getUserIds()[0]);
        ps.setLong(2, friendship.getUserIds()[1]);
        ps.setTimestamp(3, Timestamp.valueOf(friendship.getFriendsFrom()));
        return ps;
    }

    @Override
    protected PreparedStatement getUpdateQuery(Connection conn, Friendship friendship) throws SQLException {
        String sql = "UPDATE public.\"Friendship\" SET " +
                "\"uid1\"=?,  \"uid2\"=?, \"friendsFrom\"=?" +
                "WHERE \"id\"=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setLong(1, friendship.getUserIds()[0]);
        ps.setLong(2, friendship.getUserIds()[1]);
        ps.setTimestamp(3, Timestamp.valueOf(friendship.getFriendsFrom()));
        ps.setLong(4, friendship.getId());
        return ps;
    }

    @Override
    protected PreparedStatement getRemoveQuery(Connection conn, Long friendshipId) throws SQLException {
        String sql = "DELETE FROM public.\"Friendship\" WHERE \"id\" = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setLong(1, friendshipId);
        return ps;
    }

    @Override
    protected Friendship buildEntity(ResultSet result) throws SQLException {
        Long uid1 = result.getLong("uid1");
        Long uid2 = result.getLong("uid2");
        LocalDateTime friendsFrom = result.getTimestamp("friendsFrom").toLocalDateTime();
        Friendship friendship = new Friendship(uid1, uid2, friendsFrom);
        Long id = result.getLong("id");
        friendship.setId(id);
        return friendship;
    }
}
