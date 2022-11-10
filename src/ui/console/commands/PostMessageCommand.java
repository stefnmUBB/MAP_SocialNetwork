package ui.console.commands;

import domain.Message;
import repo.EntityAlreadyExistsException;

import java.time.LocalDateTime;

public class PostMessageCommand extends Command {
    public PostMessageCommand(Object[] args, CommandContext context) {
        super("post", args, context);
    }

    public Object execute() {
        var id = Long.parseLong(args[0].toString());
        var content = (String)args[1];
        var date = LocalDateTime.now();

        var message = new Message(id, content, date);

        try {
            return context.getNetwork().postMessage(message);
        }
        catch(EntityAlreadyExistsException e) {
            return "Could not post message";
        }


    }
}