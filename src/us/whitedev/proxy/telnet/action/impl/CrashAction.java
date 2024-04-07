package us.whitedev.proxy.telnet.action.impl;

import us.whitedev.proxy.function.crashers.CrashManager;
import us.whitedev.proxy.telnet.Channels;
import us.whitedev.proxy.telnet.action.Action;

import java.util.Arrays;

public class CrashAction implements Action {

    private final Channels channel = Channels.CRASH;

    @Override
    public void onAction(String[] args) {
        String name = args[0];
        int packets = Integer.parseInt(args[1]);
        CrashManager.getManager().handleMethod(name, packets);
    }

    @Override
    public Channels getChannel() {
        return channel;
    }
}
