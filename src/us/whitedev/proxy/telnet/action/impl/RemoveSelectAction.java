package us.whitedev.proxy.telnet.action.impl;

import us.whitedev.proxy.telnet.Channels;
import us.whitedev.proxy.telnet.action.Action;

import java.util.Arrays;

public class RemoveSelectAction implements Action {

    private final Channels channel = Channels.REMOVESELECT;

    @Override
    public void onAction(String[] args) {
        session.removeSelectedUser(session.getOnlineUsers().get(args[0]));
    }

    @Override
    public Channels getChannel() {
        return channel;
    }
}
