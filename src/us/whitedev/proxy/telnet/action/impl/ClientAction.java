package us.whitedev.proxy.telnet.action.impl;

import us.whitedev.proxy.function.crashers.CrashManager;
import us.whitedev.proxy.telnet.Channels;
import us.whitedev.proxy.telnet.TelnetInit;
import us.whitedev.proxy.telnet.action.Action;

import java.util.Arrays;

public class ClientAction implements Action {

    private final Channels channel = Channels.CLIENT;

    @Override
    public void onAction(String[] args) {
        for (String name : CrashManager.getManager().allMethodsList()) {
            if (name != null) {
                TelnetInit.sendMessageToUsers(Channels.CRASHLIST + name);
            }
        }
    }

    @Override
    public Channels getChannel() {
        return channel;
    }
}
