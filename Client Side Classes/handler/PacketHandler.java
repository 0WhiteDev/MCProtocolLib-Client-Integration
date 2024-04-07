package client.whitedev.mods.botter.handler;

import client.whitedev.mods.botter.telnet.TelnetInit;
import net.minecraft.network.Packet;

public interface PacketHandler {
    Class getPacket();
    TelnetInit getTelnet = TelnetInit.getInstance();
    void handlePacket(Packet packet);
}
