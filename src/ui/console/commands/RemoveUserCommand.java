package ui.console.commands;

import domain.User;
import repo.EntityAlreadyExistsException;
import reports.AbstractReport;
import service.EntityIdNotFoundException;

public class RemoveUserCommand extends Command {
    public RemoveUserCommand(Object[] args, CommandContext context) {
        super("remove-user",args, context);
    }

    public Object execute() {
        try {
            Long id = Long.parseLong(args[0].toString());
            AbstractReport report = context.getNetwork().removeUser(id);
            //System.out.println(report);
            return report;
        }
        catch (IndexOutOfBoundsException e) {
            System.out.println("Invalid number of arguments");
            System.out.println(help());
            fail();
        } catch (EntityIdNotFoundException e) {
            System.out.println("User id does not exist");
            fail();
        }
        return null;
    }

    public String help() {
        return "remove-user <ID>";
    }
}
