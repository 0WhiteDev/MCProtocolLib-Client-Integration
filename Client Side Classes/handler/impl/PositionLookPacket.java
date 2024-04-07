package client.whitedev.mods.botter.handler.impl;

import client.whitedev.mods.botter.handler.PacketHandler;
import client.whitedev.mods.botter.telnet.Channels;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;

public class PositionLookPacket implements PacketHandler {

    private final Class packet = C03PacketPlayer.C06PacketPlayerPosLook.class;

    @Override
    public void handlePacket(Packet packetIn) {
        C03PacketPlayer.C06PacketPlayerPosLook packet = (C03PacketPlayer.C06PacketPlayerPosLook) packetIn;
        String data = packet.getPositionX() + " " + packet.getPositionY() + " " + packet.getPositionZ() + " " + packet.getYaw() + " " + packet.getPitch() + " " + packet.isOnGround();
        getTelnet.getOut().println(Channels.POSLOOK + data);
    }

    @Override
    public Class getPacket() {
        return packet;
    }
}
