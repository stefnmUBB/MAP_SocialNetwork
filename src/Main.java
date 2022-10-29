import factory.CommandFactory;
import repo.EntityAlreadyExistsException;
import service.Network;
import ui.console.ConsoleUI;
import ui.console.commands.Command;

import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) throws EntityAlreadyExistsException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Network network = Network.loadDefaultNetwork();

        /*var coms = network.getCommunities();
        for(var com : coms){
            System.out.println(com);
            if(com.size()<20)
                System.out.println(com.getLongestPathLength());
        }
        System.out.println("Nr. Componente conexe = "+coms.size());*/

        ConsoleUI ui = new ConsoleUI(network);

        ui.run();

    }
}