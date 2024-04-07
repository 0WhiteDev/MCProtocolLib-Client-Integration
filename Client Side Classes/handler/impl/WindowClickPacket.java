package client.whitedev.mods.botter.handler.impl;

import client.whitedev.mods.botter.handler.PacketHandler;
import client.whitedev.mods.botter.telnet.Channels;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C0EPacketClickWindow;

public class WindowClickPacket implements PacketHandler {

    private final Class packet = C0EPacketClickWindow.class;

    @Override
    public void handlePacket(Packet packetIn) {
        C0EPacketClickWindow packet = (C0EPacketClickWindow) packetIn;
        String data = packet.getWindowId() + " " + packet.getSlotId() + " " + packet.getUsedButton() + " " + packet.getMode() + " " + packet.getActionNumber();
        getTelnet.getOut().println(Channels.WINDOW + data);
    }

    @Override
    public Class getPacket() {
        return packet;
    }
}
