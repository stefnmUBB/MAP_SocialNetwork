import config.AppContext;
import domain.Friendship;
import domain.User;
import domain.validators.UserValidator;
import factory.CommandFactory;
import repo.DatabaseRepo;
import repo.EntityAlreadyExistsException;
import repo.UserFileRepo;
import service.Network;
import ui.console.ConsoleUI;
import ui.console.commands.Command;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) throws EntityAlreadyExistsException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        /*DatabaseRepo<Long, Friendship> fdr = new DatabaseRepo<>(Friendship.class);
        fdr.add(new Friendship(10L,12L, LocalDateTime.now()));
        System.out.println(fdr.getAll());
        /*DatabaseRepo<Long, User> udr = new DatabaseRepo<>(User.class);

        UserFileRepo usersRepo =
                new UserFileRepo(AppContext.USERS_FILE_NAME, new UserValidator());

        udr.loadFromRepo(usersRepo);

        //var users = udr.getAll();
        //System.out.println(udr.remove(2L));
        //User u = new User("Mircea","Vasilescu","moi3nefur@gmail.com","1234",45);
        //udr.add(u);*/


        Network network = Network.loadDefaultNetwork();
        ConsoleUI ui = new ConsoleUI(network);
        ui.run();

    }
}