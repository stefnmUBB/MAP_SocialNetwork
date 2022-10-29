package ui.console.commands;

import java.util.Arrays;
import java.util.Objects;

public class PrintArgsCommand extends Command {
    public PrintArgsCommand(Object[] args, CommandContext context) {
        super("printargs", args, context);
    }

    public Object execute() {
        for(var arg:args) {
            print(arg.toString());
        }
        return String.join(", ", Arrays.stream(args).map(Object::toString).toArray(String[]::new));
    }
}
