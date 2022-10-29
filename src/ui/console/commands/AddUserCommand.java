package ui.console.commands;

import domain.User;
import repo.EntityAlreadyExistsException;
import reports.AbstractReport;

public class AddUserCommand extends Command {
    public AddUserCommand(Object[] args, CommandContext context) {
        super("add-user",args, context);
    }

    public Object execute() {
        try {
            User u = new User(
                    args[0].toString(),
                    args[1].toString(),
                    args[2].toString(),
                    args[3].toString(),
                    (int)Integer.parseInt(args[4].toString())
            );
            AbstractReport report = context.getNetwork().addUser(u);
            //System.out.println(report);
            return report;
        } catch (EntityAlreadyExistsException e) {
            System.out.println("User already exists");
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
        return "add-user <\"First Name\"> <\"Last Name\"> <email> <password> <age=14:100>";
    }

}
