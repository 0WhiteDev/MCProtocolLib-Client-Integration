package client.whitedev.mods.botter.functions.connection;

import client.whitedev.mods.botter.repository.UserRep;
import client.whitedev.mods.botter.telnet.Channels;
import client.whitedev.mods.botter.telnet.TelnetInit;

public class DisconnectBot {

    private final TelnetInit telnetInit = TelnetInit.getInstance();

    public void disconnect(String name){
        telnetInit.getOut().println(Channels.DISCONNECTED + name);
    }
}
