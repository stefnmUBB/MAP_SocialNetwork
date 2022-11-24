package ui.console.commands;

import java.util.concurrent.atomic.AtomicInteger;

public class ViewUsersCommand extends Command {
    public ViewUsersCommand(Object[] args, CommandContext context) {
        super("view-users",args, context);
    }

    public Object execute() {
        AtomicInteger count= new AtomicInteger();
        context.getNetwork().getAllUsers().forEach(System.out::println);
        context.getNetwork().getAllUsers().forEach(u-> count.getAndIncrement());

        System.out.println("Showing "+count+" users.");
        return null;
    }
}
