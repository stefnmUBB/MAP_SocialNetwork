package ui.console.commands;

import service.Community;

public class MSCCommand extends Command {
    public MSCCommand(Object[] args, CommandContext context) {
        super("msc",args, context);
    }

    public Object execute() {
        return context.getNetwork().mostSociableCommunity();
    }
}
