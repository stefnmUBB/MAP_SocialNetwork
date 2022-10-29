package utils.find_strategies;

import domain.User;

public abstract class UserFindStrategy {
    public abstract boolean matches(User u);
}
