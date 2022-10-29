package ui.console.commands;

import domain.Friendship;
import domain.User;

public class ViewFriendshipsCommand extends Command {
    public ViewFriendshipsCommand(Object[] args, CommandContext context) {
        super("view-friendships",args, context);
    }

    public Object execute() {
        int count=0;
        for(Friendship f :context.getNetwork().getAllFriendships()) {
            System.out.println(f);
            count++;
        }
        System.out.println("Showing "+count+" friendships.");
        return null;
    }

}
