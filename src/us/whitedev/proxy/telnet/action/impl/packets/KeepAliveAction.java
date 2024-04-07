package us.whitedev.proxy.telnet.action.impl.packets;

import us.whitedev.proxy.function.SendPacket;
import us.whitedev.proxy.telnet.Channels;
import us.whitedev.proxy.telnet.action.Action;

public class KeepAliveAction implements Action {

    private final Channels channel = Channels.KEEPALIVE;

    @Override
    public void onAction(String[] args) {
        int key = Integer.parseInt(args[0]);
        SendPacket.sendKeepAlivePacket(key);
    }

    @Override
    public Channels getChannel() {
        return channel;
    }
}
