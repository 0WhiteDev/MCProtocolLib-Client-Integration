package client.whitedev.mods.botter.functions.connection;

import client.whitedev.mods.botter.telnet.Channels;
import client.whitedev.mods.botter.telnet.TelnetInit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerLoginClient;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.login.client.C00PacketLoginStart;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class SessionConnector {

    private final Minecraft mc = Minecraft.getMinecraft();
    private final TelnetInit telnetInit = TelnetInit.getInstance();

    public void connectToProxyServer(String ip, String name, String password) {
        try {
            String[] address = addressSplitter(ip);
            telnetInit.getOut().println(Channels.SESSIONCONNECT + address[0] + " " + address[1] + " " + name + " " + (password == null || password.equals("") ? "non-premium" : password));
            mc.theWorld.sendQuittingDisconnectingPacket();
            InetAddress inetaddress = InetAddress.getByName("0.0.0.0");
            NetworkManager networkManager = NetworkManager.createNetworkManagerAndConnect(inetaddress, 1339, mc.gameSettings.isUsingNativeTransport());
            networkManager.setNetHandler(new NetHandlerLoginClient(networkManager, mc, null));
            networkManager.sendPacket(new C00Handshake(47, "0.0.0.0", 1339, EnumConnectionState.LOGIN));
            networkManager.sendPacket(new C00PacketLoginStart(mc.getSession().getProfile()));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    private String[] addressSplitter(String ip){
        String[] address;
        if (ip.contains(":")) {
            address = ip.split(":");
        }else{
            address = new String[]{ip, "25565"};
        }
        return address;
    }

}
