package client.whitedev.mods.botter.handler.impl;

import client.whitedev.mods.botter.handler.PacketHandler;
import client.whitedev.mods.botter.telnet.Channels;
import client.whitedev.mods.botter.telnet.TelnetInit;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;

public class LookPacket implements PacketHandler {

    private final Class packet = C03PacketPlayer.C05PacketPlayerLook.class;

    @Override
    public void handlePacket(Packet packetIn) {
        C03PacketPlayer.C05PacketPlayerLook packet = (C03PacketPlayer.C05PacketPlayerLook) packetIn;
        String data = packet.getYaw() + " " + packet.getPitch() + " " + packet.isOnGround();
        getTelnet.getOut().println(Channels.LOOK + data);
    }

    @Override
    public Class getPacket() {
        return packet;
    }
}
