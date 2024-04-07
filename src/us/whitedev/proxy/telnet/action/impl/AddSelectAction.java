package us.whitedev.proxy.telnet.action.impl;

import us.whitedev.proxy.telnet.Channels;
import us.whitedev.proxy.telnet.action.Action;

import java.util.Arrays;

public class AddSelectAction implements Action {

    private final Channels channel = Channels.ADDSELECT;

    @Override
    public void onAction(String[] args) {
        session.addSelectedUser(session.getOnlineUsers().get(args[0]));
    }

    @Override
    public Channels getChannel() {
        return channel;
    }
}
