package ui.console;

import domain.validators.ValidationException;
import factory.CommandFactory;
import factory.InnerCommandFailedException;
import service.Network;
import ui.console.commands.Command;
import ui.console.commands.CommandContext;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import java.util.Scanner;

public class ConsoleUI {
    private final Network network;

    Scanner scan = new Scanner(System.in);

    public ConsoleUI(Network network) {
        this.network = network;
    }

    private void treatUnknownException(Exception e) {
        System.out.println("An unexpected runtime error happened.");
        System.out.println(e.getMessage());
        e.printStackTrace();
    }

    public boolean runStep() {
        System.out.print(">>> ");
        String line = scan.nextLine();
        try {
            Command cmd = CommandFactory.getInstance().generateCommand(line, new CommandContext(network));
            if(cmd==null) {
                System.out.println("Unknown command");
                return true;
            }
            if(Objects.equals(cmd.getName(),"exit")) {
                return false;
            }
            try {
                Object o = cmd.execute();
                System.out.println("Command result : " + (o==null ? "Null" : o));
            } catch(ValidationException e) {
                System.out.println("Could not execute operation.");
                System.out.println(e.getMessage());

            }

        } catch (NoSuchMethodException | InvocationTargetException |
                 InstantiationException | IllegalAccessException e) {
            treatUnknownException(e);
            return true;
        } catch (InnerCommandFailedException e) {
            System.out.println("Inner command failed");
            return true;
        }
        return true;
    }

    public void run() {
        while(runStep());
    }
}