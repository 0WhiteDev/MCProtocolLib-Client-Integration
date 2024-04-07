package client.whitedev.mods.botter.handler.impl;

import client.whitedev.mods.botter.handler.PacketHandler;
import client.whitedev.mods.botter.telnet.Channels;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C00PacketKeepAlive;

public class SlotPacket implements PacketHandler {

    private final Class packet = C00PacketKeepAlive.class;

    @Override
    public void handlePacket(Packet packetIn) {
        C00PacketKeepAlive packet = (C00PacketKeepAlive) packetIn;
        String data = String.valueOf(packet.getKey());
        getTelnet.getOut().println(Channels.KEEPALIVE + data);
    }

    @Override
    public Class getPacket() {
        return packet;
    }
}
