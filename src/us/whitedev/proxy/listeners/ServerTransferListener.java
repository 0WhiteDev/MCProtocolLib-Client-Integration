package us.whitedev.proxy.listeners;

import org.spacehq.mc.protocol.data.game.values.ClientRequest;
import org.spacehq.mc.protocol.data.game.values.scoreboard.ObjectiveAction;
import org.spacehq.mc.protocol.data.game.values.scoreboard.ScoreType;
import org.spacehq.mc.protocol.data.game.values.scoreboard.ScoreboardPosition;
import org.spacehq.mc.protocol.packet.ingame.client.ClientKeepAlivePacket;
import org.spacehq.mc.protocol.packet.ingame.client.ClientRequestPacket;
import org.spacehq.mc.protocol.packet.ingame.server.ServerChatPacket;
import org.spacehq.mc.protocol.packet.ingame.server.ServerJoinGamePacket;
import org.spacehq.mc.protocol.packet.ingame.server.ServerRespawnPacket;
import org.spacehq.mc.protocol.packet.ingame.server.entity.ServerEntityEquipmentPacket;
import org.spacehq.mc.protocol.packet.ingame.server.entity.ServerEntityMetadataPacket;
import org.spacehq.mc.protocol.packet.ingame.server.entity.player.ServerPlayerPositionRotationPacket;
import org.spacehq.mc.protocol.packet.ingame.server.scoreboard.ServerDisplayScoreboardPacket;
import org.spacehq.mc.protocol.packet.ingame.server.scoreboard.ServerScoreboardObjectivePacket;
import org.spacehq.mc.protocol.packet.ingame.server.window.ServerCloseWindowPacket;
import org.spacehq.mc.protocol.packet.login.server.EncryptionRequestPacket;
import org.spacehq.mc.protocol.packet.login.server.LoginSetCompressionPacket;
import org.spacehq.mc.protocol.packet.login.server.LoginSuccessPacket;
import org.spacehq.packetlib.Client;
import org.spacehq.packetlib.Session;
import org.spacehq.packetlib.event.session.DisconnectedEvent;
import org.spacehq.packetlib.event.session.PacketReceivedEvent;
import org.spacehq.packetlib.event.session.SessionAdapter;
import org.spacehq.packetlib.packet.Packet;
import us.whitedev.proxy.repository.ClientRep;
import us.whitedev.proxy.repository.SessionsRep;
import us.whitedev.proxy.repository.UserRep;
import us.whitedev.proxy.telnet.Channels;
import us.whitedev.proxy.telnet.TelnetInit;

public class ServerTransferListener extends SessionAdapter {

    private final Session originalSession;
    private final Client client;
    private final UserRep userRep = UserRep.getInstance();

    public ServerTransferListener(Session originalSession, Client client) {
        this.originalSession = originalSession;
        this.client = client;
    }

    @Override
    public void packetReceived(PacketReceivedEvent event) {
        Packet packet = event.getPacket();
        if(packet instanceof LoginSuccessPacket) TelnetInit.sendMessageToUsers(Channels.JOIN.name() + userRep.getUsername());
        if (!(packet instanceof LoginSetCompressionPacket || packet instanceof LoginSuccessPacket || packet instanceof EncryptionRequestPacket)) {
            if (packet instanceof ServerJoinGamePacket) {
                originalSession.send(packet);
                ServerJoinGamePacket joinGamePacket = (ServerJoinGamePacket) packet;
                spawnPlayer(joinGamePacket);
            } else if (packet instanceof ServerPlayerPositionRotationPacket) {
                originalSession.send(packet);
            } else if (packet instanceof ServerEntityMetadataPacket) {
                originalSession.send(packet);
            } else if (packet instanceof ServerEntityEquipmentPacket) {
                originalSession.send(packet);
            } else if (packet instanceof ServerChatPacket) {
                originalSession.send(packet);
            } else {
                originalSession.send(packet);
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
        originalSession.disconnect("§cDisconnected, reason: §4" + event.getReason());
    }

    private void spawnPlayer(ServerJoinGamePacket packet) {
        originalSession.send(new ServerJoinGamePacket(packet.getEntityId(), packet.getHardcore(), packet.getGameMode(), 1, packet.getDifficulty(), packet.getMaxPlayers(), packet.getWorldType(), packet.getReducedDebugInfo()));
        originalSession.send(new ServerRespawnPacket(packet.getDimension(), packet.getDifficulty(), packet.getGameMode(), packet.getWorldType()));
        originalSession.send(new ServerCloseWindowPacket(0));
        originalSession.send(new ServerScoreboardObjectivePacket("advertise1", ObjectiveAction.ADD, "§7Connected with §cBotService", ScoreType.INTEGER));
        originalSession.send(new ServerScoreboardObjectivePacket("advertise2", ObjectiveAction.ADD, "§7Created by §40WhiteDev & madeq", ScoreType.HEARTS));
        originalSession.send(new ServerDisplayScoreboardPacket(ScoreboardPosition.SIDEBAR, "advertise1"));
        originalSession.send(new ServerDisplayScoreboardPacket(ScoreboardPosition.BELOW_NAME, "advertise2"));
        client.getSession().send(new ClientRequestPacket(ClientRequest.RESPAWN));
    }
}
