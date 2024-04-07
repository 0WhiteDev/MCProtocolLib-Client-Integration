package us.whitedev.proxy.listeners;

import org.spacehq.mc.protocol.packet.ingame.client.ClientKeepAlivePacket;
import org.spacehq.mc.protocol.packet.ingame.client.player.ClientPlayerMovementPacket;
import org.spacehq.mc.protocol.packet.ingame.client.player.ClientPlayerPositionRotationPacket;
import org.spacehq.mc.protocol.packet.ingame.client.player.ClientPlayerRotationPacket;
import org.spacehq.packetlib.event.session.DisconnectedEvent;
import org.spacehq.packetlib.event.session.PacketReceivedEvent;
import org.spacehq.packetlib.event.session.SessionAdapter;
import org.spacehq.packetlib.packet.Packet;
import us.whitedev.proxy.repository.ClientRep;
import us.whitedev.proxy.repository.SessionsRep;
import us.whitedev.proxy.repository.UserRep;
import us.whitedev.proxy.telnet.Channels;
import us.whitedev.proxy.telnet.TelnetInit;

public class ClientTransferListener extends SessionAdapter {

    private final ClientRep clientRep = ClientRep.getInstance();
    private final UserRep userRep = UserRep.getInstance();

    @Override
    public void packetReceived(PacketReceivedEvent event) {
        Packet packet;
        if (clientRep.getClient().getSession().isConnected()) {
            if (event.getPacket() instanceof ClientPlayerMovementPacket) {
                packet = event.getPacket();
                if (packet instanceof ClientPlayerPositionRotationPacket) {
                    clientRep.setLocation(((ClientPlayerPositionRotationPacket) packet).getX(), ((ClientPlayerPositionRotationPacket) packet).getY(), ((ClientPlayerPositionRotationPacket) packet).getZ(), (float) ((ClientPlayerPositionRotationPacket) packet).getYaw(), (float) ((ClientPlayerPositionRotationPacket) packet).getPitch(), ((ClientPlayerPositionRotationPacket) packet).isOnGround());
                } else if (packet instanceof ClientPlayerRotationPacket) {
                    clientRep.setRotation((float) ((ClientPlayerRotationPacket) packet).getYaw(), (float) ((ClientPlayerRotationPacket) packet).getPitch(), ((ClientPlayerRotationPacket) packet).isOnGround());
                }
            }else {
                if(!(event.getPacket() instanceof ClientKeepAlivePacket)) clientRep.getClient().getSession().send(event.getPacket());
            }
        }
    }

    @Override
    public void disconnected(DisconnectedEvent event) {
        if (event.getCause() != null) {
            event.getCause().printStackTrace();
        }
        SessionsRep.getInstance().removeOnlineUser(userRep.getUsername());
        TelnetInit.sendMessageToUsers(Channels.DISCONNECTED.name() + userRep.getUsername());
        clientRep.getClient().getSession().disconnect("original session disconnected");
    }
}