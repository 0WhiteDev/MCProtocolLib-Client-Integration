package client.whitedev.mods.botter.handler.impl;

import client.whitedev.mods.botter.handler.PacketHandler;
import client.whitedev.mods.botter.telnet.Channels;
import client.whitedev.mods.botter.telnet.TelnetInit;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;

public class KeepAlivePacket implements PacketHandler {

    private final Class packet = C09PacketHeldItemChange.class;

    @Override
    public void handlePacket(Packet packetIn) {
        C09PacketHeldItemChange packet = (C09PacketHeldItemChange) packetIn;
        String data = String.valueOf(packet.getSlotId());
        getTelnet.getOut().println(Channels.SLOT + data);
    }

    @Override
    public Class getPacket() {
        return packet;
    }
}
