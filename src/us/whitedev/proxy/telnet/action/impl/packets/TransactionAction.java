package us.whitedev.proxy.telnet.action.impl.packets;

import us.whitedev.proxy.function.SendPacket;
import us.whitedev.proxy.telnet.Channels;
import us.whitedev.proxy.telnet.action.Action;

public class TransactionAction implements Action {

    private final Channels channel = Channels.TRANSACTION;

    @Override
    public void onAction(String[] args) {
        int windowId = Integer.parseInt(args[0]);
        int actionId = Integer.parseInt(args[1]);
        boolean accepted = args[2].equals("true");
        SendPacket.sendTransactionPacket(windowId, actionId, accepted);
    }

    @Override
    public Channels getChannel() {
        return channel;
    }
}
