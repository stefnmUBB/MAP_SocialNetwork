package ui.console.commands;

import service.Community;

public class LongestDistanceCommand extends Command {
    public LongestDistanceCommand(Object[] args, CommandContext context) {
        super("longest-path", args, context);
    }

    public Object execute() {
        var com = (Community)args[0];
        return com.getLongestPathLength();
    }
}
