package ui.console.commands;

import domain.Friendship;
import domain.User;

import java.util.concurrent.atomic.AtomicInteger;

public class ViewFriendshipsCommand extends Command {
    public ViewFriendshipsCommand(Object[] args, CommandContext context) {
        super("view-friendships",args, context);
    }

    public Object execute() {
        AtomicInteger count= new AtomicInteger();
        context.getNetwork().getAllFriendships().forEach(System.out::println);
        context.getNetwork().getAllFriendships().forEach(f-> count.getAndIncrement());
        System.out.println("Showing "+count+" friendships.");
        return null;
    }

}
