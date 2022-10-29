package ui.console.commands;

import domain.Friendship;
import repo.EntityAlreadyExistsException;
import reports.AbstractReport;
import service.EntityIdNotFoundException;

public class RemoveFriendshipCommand extends Command {
    public RemoveFriendshipCommand(Object[] args, CommandContext context) {
        super("remove-frienship", args, context);
    }

    public Object execute() {
        try {
            Long id = Long.parseLong(args[0].toString());
            AbstractReport report = context.getNetwork().removeFriendship(id);
            //System.out.println(report);
            return report;
        }
        catch (IndexOutOfBoundsException e) {
            System.out.println("Invalid number of arguments");
            System.out.println(help());
            fail();
        } catch (EntityIdNotFoundException e) {
            System.out.println("Friendship id does not exist");
            fail();
        }
        return null;
    }
    public String help() {
        return "add-friendship <friendshipID>";
    }

}
