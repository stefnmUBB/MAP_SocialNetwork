package ui.console.commands;

public class ViewMessagesFromCommand extends Command {
    public ViewMessagesFromCommand(Object[] args, CommandContext context) {
        super("messages-from", args, context);
    }

    public Object execute() {
        var id = Long.parseLong(args[0].toString());
        return context.getNetwork().getMessagesFrom(id);
    }
}
