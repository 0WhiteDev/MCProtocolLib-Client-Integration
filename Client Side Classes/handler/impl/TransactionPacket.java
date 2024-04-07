package client.whitedev.mods.botter.handler.impl;

import client.whitedev.mods.botter.handler.PacketHandler;
import client.whitedev.mods.botter.telnet.Channels;
import client.whitedev.mods.botter.telnet.TelnetInit;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;

public class TransactionPacket implements PacketHandler {

    private final Class packet = C0FPacketConfirmTransaction.class;

    @Override
    public void handlePacket(Packet packetIn) {
        C0FPacketConfirmTransaction packet = (C0FPacketConfirmTransaction) packetIn;
        String data = packet.getWindowId() + " " + (int) packet.getUid() + " " + packet.isAccepted();
        getTelnet.getOut().println(Channels.TRANSACTION + data);
    }

    @Override
    public Class getPacket() {
        return packet;
    }
}
