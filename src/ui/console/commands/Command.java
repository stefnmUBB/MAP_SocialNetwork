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

    public Command(String name, Object[] args, CommandContext context) {
        this.name = name;
        this.args = args;
        this.context = context;
    }

    boolean outputDisabled = false;

    public void disableOutput() {
        outputDisabled = true;
    }

    protected void print(String s) {
        if(outputDisabled) return;
        System.out.println(s);
    }

    private boolean hasfailed = false;

    public void fail() {
        hasfailed = true;
    }

    public boolean hasFailed() {
        return hasfailed;
    }

    public Object execute() {
        return null;
    }

    public String getName() {
        return name;
    }

    public Object[] getArgs() {
        return args;
    }

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

    public String help() { return "";}
}
