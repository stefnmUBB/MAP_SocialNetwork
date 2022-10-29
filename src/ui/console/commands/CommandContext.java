package ui.console.commands;

import service.Network;

public class CommandContext {
    private final Network network;
    public CommandContext(Network network) {
        this.network = network;
    }

    public Network getNetwork() {
        return network;
    }
}
