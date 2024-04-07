package client.whitedev.mods.botter.handler.impl;

import client.whitedev.mods.botter.handler.PacketHandler;
import client.whitedev.mods.botter.telnet.Channels;
import client.whitedev.mods.botter.telnet.TelnetInit;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C0EPacketClickWindow;

public class CloseWindowPacket implements PacketHandler {

    private final Class packet = C0DPacketCloseWindow.class;

    @Override
    public void handlePacket(Packet packetIn) {
        C0DPacketCloseWindow packet = (C0DPacketCloseWindow) packetIn;
        String data = packet.getWindowId() + "";
        getTelnet.getOut().println(Channels.WCLOSE + data);
    }

    @Override
    public Class getPacket() {
        return packet;
    }
}
