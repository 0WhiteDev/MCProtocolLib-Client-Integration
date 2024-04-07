package client.whitedev.mods.botter.handler.impl;

import client.whitedev.mods.botter.handler.PacketHandler;
import client.whitedev.mods.botter.telnet.Channels;
import client.whitedev.mods.botter.telnet.TelnetInit;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0APacketAnimation;

public class AnimationPacket implements PacketHandler {

    private final Class packet = C0APacketAnimation.class;

    @Override
    public void handlePacket(Packet packetIn) {
        getTelnet.getOut().println(Channels.ARM);
    }

    @Override
    public Class getPacket() {
        return packet;
    }
}
