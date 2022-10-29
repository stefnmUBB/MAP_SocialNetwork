package ui.console.commands;

import domain.Friendship;

public class CommunitiesCountCommand extends Command {
    public CommunitiesCountCommand(Object[] args, CommandContext context)
    {
        super("communities-count",args, context);
    }

    public Object execute() {
        Integer cnt = context.getNetwork().communitiesCount();
        //System.out.println(context.getNetwork().communitiesCount());
        return cnt;
    }
}
