package ui.console.commands;

import service.Community;

public class CommunityOfCommand extends Command {
    public CommunityOfCommand(Object[] args, CommandContext context) {
        super("community-of",args, context);
    }

    public Object execute() {
        var id = Long.parseLong(args[0].toString());
        return new Community(context.getNetwork(), id);
    }
}
