package us.whitedev.proxy.telnet.action.impl;

import us.whitedev.proxy.telnet.Channels;
import us.whitedev.proxy.telnet.action.Action;

public class DisconnectAction implements Action {

    private final Channels channel = Channels.DISCONNECTED;

    @Override
    public void onAction(String[] args) {
        sessionAction.disconnectSession(args);
    }

    @Override
    public Channels getChannel() {
        return channel;
    }
}
