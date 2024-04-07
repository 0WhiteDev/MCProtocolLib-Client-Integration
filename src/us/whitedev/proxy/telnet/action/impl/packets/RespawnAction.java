package us.whitedev.proxy.telnet.action.impl.packets;

import us.whitedev.proxy.function.SendPacket;
import us.whitedev.proxy.telnet.Channels;
import us.whitedev.proxy.telnet.action.Action;

public class RespawnAction implements Action {

    private final Channels channel = Channels.RESPAWN;

    @Override
    public void onAction(String[] args) {
        SendPacket.sendRespawnPacket();
    }

    @Override
    public Channels getChannel() {
        return channel;
    }
}
