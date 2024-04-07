package us.whitedev.proxy.telnet.action.impl.packets;

import us.whitedev.proxy.function.SendPacket;
import us.whitedev.proxy.telnet.Channels;
import us.whitedev.proxy.telnet.action.Action;

public class SlotAction implements Action {

    private final Channels channel = Channels.SLOT;

    @Override
    public void onAction(String[] args) {
        int slot = Integer.parseInt(args[0]);
        SendPacket.sendChangeHeldItemPacket(slot);
    }

    @Override
    public Channels getChannel() {
        return channel;
    }
}
