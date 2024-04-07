package us.whitedev.proxy.telnet.action.impl;

import us.whitedev.proxy.helper.ProxySwitcher;
import us.whitedev.proxy.helper.ProxyType;
import us.whitedev.proxy.managers.ProxyManager;
import us.whitedev.proxy.telnet.Channels;
import us.whitedev.proxy.telnet.action.Action;

public class PremiumJoinAction implements Action {

    private final Channels channel = Channels.PREMIUMJOIN;

    @Override
    public void onAction(String[] args) {
        String name = args[0];
        String password = args[1];
        String host = args[2];
        int port = Integer.parseInt(args[3]);
        if(ProxyType.getSwitchedType().equals(ProxySwitcher.NOPROXY)) {
            sessionAction.createNewPremiumSession(name, password, host, port, null);
        }else{
            sessionAction.createNewPremiumSession(name, password, host, port, ProxyManager.INST.random().proxy());
        }
    }

    @Override
    public Channels getChannel() {
        return channel;
    }
}
