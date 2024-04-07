package us.whitedev.proxy.telnet.action.impl;

import us.whitedev.proxy.repository.UserRep;
import us.whitedev.proxy.telnet.Channels;
import us.whitedev.proxy.telnet.action.Action;

import java.util.Arrays;

public class SessionConnectAction implements Action {

    private final Channels channel = Channels.SESSIONCONNECT;

    @Override
    public void onAction(String[] args) {
        UserRep userRep = UserRep.getInstance();
        userRep.setServerIp(args[0]);
        userRep.setServerPort(Integer.parseInt(args[1]));
        userRep.setUsername(args[2]);
        userRep.setPassword(args[3]);
    }

    @Override
    public Channels getChannel() {
        return channel;
    }
}
