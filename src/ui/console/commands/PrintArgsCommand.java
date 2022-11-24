package ui.console.commands;

import java.util.Arrays;
import java.util.Objects;

public class PrintArgsCommand extends Command {
    public PrintArgsCommand(Object[] args, CommandContext context) {
        super("printargs", args, context);
    }

    public Object execute() {
        Arrays.stream(args).map(Object::toString).forEach(this::print);
        return String.join(", ", Arrays.stream(args).map(Object::toString).toArray(String[]::new));
    }
}
