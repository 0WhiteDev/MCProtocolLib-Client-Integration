package client.whitedev.mods.botter.functions;

import client.whitedev.mods.botter.telnet.Channels;
import client.whitedev.mods.botter.telnet.TelnetInit;

public class SendCrashPacket {
    private final TelnetInit telnetInit = TelnetInit.getInstance();

    public void sendCrash(String name, String packets){
        telnetInit.getOut().println(Channels.CRASH + name + " " + packets);
    }
}
