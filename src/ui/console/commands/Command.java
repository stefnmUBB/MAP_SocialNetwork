package ui.console.commands;

import factory.CommandFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Command {
    String name;
    Object[] args;
    protected CommandContext context;

    /**
     * Creates a new command with name and args in a certain context
     * @param name command name
     * @param args command args
     * @param context command context
     */
    public Command(String name, Object[] args, CommandContext context) {
        this.name = name;
        this.args = args;
        this.context = context;
    }

    boolean outputDisabled = false;

    /**
     * make command not print anything on screen. Good for inner commands
     */
    public void disableOutput() {
        outputDisabled = true;
    }

    /**
     * wrap around System.out.println with output disabled check
     * @param s string to print
     */
    protected void print(String s) {
        if(outputDisabled) return;
        System.out.println(s);
    }

    private boolean hasfailed = false;

    /**
     * make command fail
     */
    public void fail() {
        hasfailed = true;
    }

    /**
     * @return true if command failed
     */
    public boolean hasFailed() {
        return hasfailed;
    }

    /**
     * execute command
     * @return command can optionally return an object that can be printed out. Good for inline commands
     */
    public Object execute() {
        return null;
    }

    /**
     * @return Command name
     */
    public String getName() {
        return name;
    }

    /**
     * @return Command args list
     */
    public Object[] getArgs() {
        return args;
    }

    /**
     * parse command from string
     * @param str command line
     * @param context command context
     * @return generated command
     */
    public static Command fromString(String str, CommandContext context) {
        List<String> list = new ArrayList<String>();
        Matcher m = Pattern.compile("([^\\`\"]\\S*|\".+?\"|\\`.+?\\`)\\s*").matcher(str);
        while (m.find()) {
            var token = m.group(1);
            if(token.charAt(0)=='"')
                token = token.replace("\"", "");
            list.add(token);
        }
        if(list.size()==0) {
            return null;
        }
        String name = list.get(0);
        list.remove(0);
        Object[] arr = list.toArray();
        return new Command(name, Arrays.copyOf(arr, arr.length, String[].class), context);

    }

    /**
     * get help message for the command
     * @return help message
     */
    public String help() { return "";}
}
