import config.AppContext;
import domain.Friendship;
import domain.User;
import domain.validators.UserValidator;
import factory.CommandFactory;
import org.postgresql.util.PSQLException;
import repo.DatabaseRepo;
import repo.EntityAlreadyExistsException;
import repo.UserFileRepo;
import service.Network;
import ui.console.ConsoleUI;
import ui.console.commands.Command;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) throws EntityAlreadyExistsException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        try {
            Network network = Network.loadDefaultNetwork();
            ConsoleUI ui = new ConsoleUI(network);
            ui.run();
        }catch (SQLException e) {
            System.out.println("Database error");
            System.out.println(e.getMessage());
            return;
        }

    }
}