package us.whitedev.proxy.telnet.action.impl.packets;

import us.whitedev.proxy.function.SendPacket;
import us.whitedev.proxy.telnet.Channels;
import us.whitedev.proxy.telnet.action.Action;

public class ArmAction implements Action {

    private final Channels channel = Channels.ARM;

    @Override
    public void onAction(String[] args) {
        SendPacket.sendArmAnimationPacket();
    }

    @Override
    public Channels getChannel() {
        return channel;
    }
}
