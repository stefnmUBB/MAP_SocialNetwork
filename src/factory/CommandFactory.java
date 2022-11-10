package factory;

import ui.console.commands.*;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

public class CommandFactory {
    private CommandFactory() {}
    private static final CommandFactory instance = new CommandFactory();
    public static CommandFactory getInstance() { return instance; }

    private static final Map<String, Class> types = new HashMap<>();

    static {
        types.put("exit", ExitCommand.class);
        types.put("add-user", AddUserCommand.class);
        types.put("addu", AddUserCommand.class);

        types.put("remove-user", RemoveUserCommand.class);
        types.put("rmu", RemoveUserCommand.class);


        types.put("view-users", ViewUsersCommand.class);
        types.put("vu", ViewUsersCommand.class);

        types.put("add-friendship", AddFriendshipCommand.class);
        types.put("addf", AddFriendshipCommand.class);

        types.put("remove-friendship", RemoveFriendshipCommand.class);
        types.put("rmf", RemoveFriendshipCommand.class);

        types.put("view-friendships", ViewFriendshipsCommand.class);
        types.put("vf", ViewFriendshipsCommand.class);

        types.put("communities-count", CommunitiesCountCommand.class);
        types.put("comcnt", CommunitiesCountCommand.class);

        types.put("community-of", CommunityOfCommand.class);
        types.put("longest-path", LongestDistanceCommand.class);

        types.put("printargs", PrintArgsCommand.class);

        types.put("msc", MSCCommand.class);

        types.put("messages-from", ViewMessagesFromCommand.class);
        types.put("post", PostMessageCommand.class);

    };

    public Command generateCommand(String cmd, CommandContext context)
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, InnerCommandFailedException {
        Command c = Command.fromString(cmd, context);
        if(c==null) return c;
        var name = c.getName();
        var args = c.getArgs();
        ArrayList<Object> nargs = new ArrayList<>();

        for(var arg:args) {
            if(arg instanceof String) {
                String _arg = (String)arg;
                if (_arg.charAt(0) == '`') {
                    String _c = _arg.replace("`", "");
                    var innercmd = generateCommand(_c, context);
                    if (innercmd == null) {
                        throw new InnerCommandFailedException();
                    }
                    innercmd.disableOutput();
                    var res = innercmd.execute();
                    if (innercmd.hasFailed()) {
                        throw new InnerCommandFailedException();
                    }
                    nargs.add(res);
                }
                else nargs.add(arg);
            }
            else nargs.add(arg);
        }

        if(!types.containsKey(name)) {
            return null;
        }
        Object[] sargs = nargs.stream().toArray(Object[]::new);

        Command result = (Command)(types.get(name)
                .getConstructor(Object[].class, CommandContext.class)
                .newInstance((Object) sargs, context));
        return result;
    }
}
