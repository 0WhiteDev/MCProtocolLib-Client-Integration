package client.whitedev.mods.botter.functions;

import client.whitedev.mods.botter.telnet.Channels;
import client.whitedev.mods.botter.telnet.TelnetInit;

public class RespawnBot {
    private final TelnetInit telnetInit = TelnetInit.getInstance();

    public void sendRespawnPacket(){
        telnetInit.getOut().println(Channels.RESPAWN);
    }
}
