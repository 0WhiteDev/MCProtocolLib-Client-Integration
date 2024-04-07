package us.whitedev.proxy.telnet.action.impl.packets;

import us.whitedev.proxy.function.SendPacket;
import us.whitedev.proxy.telnet.Channels;
import us.whitedev.proxy.telnet.action.Action;

public class WindowCloseAction implements Action {

    private final Channels channel = Channels.WCLOSE;

    @Override
    public void onAction(String[] args) {
        int windowid = Integer.parseInt(args[0]);
        SendPacket.sendWClosePacket(windowid);
    }

    @Override
    public Channels getChannel() {
        return channel;
    }
}
