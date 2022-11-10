package ui.console.commands;

import domain.Friendship;
import domain.User;
import repo.EntityAlreadyExistsException;
import reports.AbstractReport;

import java.time.LocalDateTime;

public class AddFriendshipCommand extends Command {
    public AddFriendshipCommand(Object[] args, CommandContext context) {
        super("add-frienship", args, context);
    }

    public Object execute() {
        try {
            Friendship f = new Friendship(
                Long.parseLong((String)args[0]),
                Long.parseLong((String)args[1]),
                LocalDateTime.now()
            );
            AbstractReport report = context.getNetwork().addFriendship(f);
            return report;
        } catch (EntityAlreadyExistsException e) {
            System.out.println("Frienship already exists");
            fail();
        }
        catch (IndexOutOfBoundsException e) {
            System.out.println("Invalid number of arguments");
            System.out.println(help());
            fail();
        }
        return null;
    }
    public String help() {
        return "add-friendship <userID1> <userID2>";
    }

}
