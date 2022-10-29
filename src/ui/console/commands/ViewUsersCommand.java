package ui.console.commands;

import domain.User;
import reports.AbstractReport;

public class ViewUsersCommand extends Command {
    public ViewUsersCommand(Object[] args, CommandContext context) {
        super("view-users",args, context);
    }

    public Object execute() {
        int count=0;
        for(User u :context.getNetwork().getAllUsers()) {
            System.out.println(u);
            count++;
        }
        System.out.println("Showing "+count+" users.");
        return null;
    }
}
