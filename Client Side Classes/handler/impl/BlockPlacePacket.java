package client.whitedev.mods.botter.handler.impl;

import client.whitedev.mods.botter.handler.PacketHandler;
import client.whitedev.mods.botter.telnet.Channels;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;

public class BlockPlacePacket implements PacketHandler {

    private final Class packet = C08PacketPlayerBlockPlacement.class;

    @Override
    public void handlePacket(Packet packetIn) {
        C08PacketPlayerBlockPlacement packet = (C08PacketPlayerBlockPlacement) packetIn;
        String data = packet.getPosition().getX() + " " + packet.getPosition().getY() + " " + packet.getPosition().getZ() + " " + packet.getPlacedBlockDirection() + " " + packet.getPlacedBlockOffsetX() + " " + packet.getPlacedBlockOffsetY() + " " + packet.getPlacedBlockOffsetZ();
        getTelnet.getOut().println(Channels.BLOCKPLACE + data);
    }

    @Override
    public Class getPacket() {
        return packet;
    }
}
