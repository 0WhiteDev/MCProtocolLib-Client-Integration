package us.whitedev.proxy.telnet.action.impl;

import us.whitedev.proxy.helper.ProxySwitcher;
import us.whitedev.proxy.helper.ProxyType;
import us.whitedev.proxy.managers.ProxyManager;
import us.whitedev.proxy.telnet.Channels;
import us.whitedev.proxy.telnet.action.Action;

import java.io.IOException;

public class ProxyAction implements Action {

    private final Channels channel = Channels.PROXY;

    @Override
    public void onAction(String[] args) {
        String type = args[0];
        try {
            switch (type) {
                case "NOPROXY":
                    ProxyType.setProxySwitcher(ProxySwitcher.NOPROXY);
                    break;
                case "SOCKS4":
                    ProxyType.setProxySwitcher(ProxySwitcher.SOCKS4);
                    ProxyManager.INST.loadProxies("socks4.txt", ProxyType.Type.SOCKS4);
                    break;
                case "SOCKS5":
                    ProxyType.setProxySwitcher(ProxySwitcher.SOCKS5);
                    ProxyManager.INST.loadProxies("socks5.txt", ProxyType.Type.SOCKS5);
                    break;
                case "HTTP":
                    ProxyType.setProxySwitcher(ProxySwitcher.HTTP);
                    ProxyManager.INST.loadProxies("http.txt", ProxyType.Type.HTTP);
                    break;
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public Channels getChannel() {
        return channel;
    }
}
