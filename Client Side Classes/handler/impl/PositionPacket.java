package client.whitedev.mods.botter.handler.impl;

import client.whitedev.mods.botter.handler.PacketHandler;
import client.whitedev.mods.botter.telnet.Channels;
import client.whitedev.mods.botter.telnet.TelnetInit;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0EPacketClickWindow;

public class PositionPacket implements PacketHandler {

    private final Class packet = C03PacketPlayer.C04PacketPlayerPosition.class;

    @Override
    public void handlePacket(Packet packetIn) {
        C03PacketPlayer.C04PacketPlayerPosition packet = (C03PacketPlayer.C04PacketPlayerPosition) packetIn;
        String data = packet.getPositionX() + " " + packet.getPositionY() + " " + packet.getPositionZ() + " " + packet.getPositionZ();
        getTelnet.getOut().println(Channels.MOVE + data);
    }

    @Override
    public Class getPacket() {
        return packet;
    }
}
