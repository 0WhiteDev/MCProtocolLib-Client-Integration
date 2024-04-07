package us.whitedev.proxy.telnet.action.impl;

import us.whitedev.proxy.helper.ProxySwitcher;
import us.whitedev.proxy.helper.ProxyType;
import us.whitedev.proxy.managers.ProxyManager;
import us.whitedev.proxy.telnet.Channels;
import us.whitedev.proxy.telnet.action.Action;

public class JoinAction implements Action {

    private final Channels channel = Channels.JOIN;

    @Override
    public void onAction(String[] args) {
        String name = args[0];
        String host = args[1];
        int port = Integer.parseInt(args[2]);
        if(ProxyType.getSwitchedType().equals(ProxySwitcher.NOPROXY)) {
            sessionAction.createNewSession(name, host, port, null);
        }else{
            sessionAction.createNewSession(name, host, port, ProxyManager.INST.random().proxy());
        }
    }

    @Override
    public Channels getChannel() {
        return channel;
    }
}
