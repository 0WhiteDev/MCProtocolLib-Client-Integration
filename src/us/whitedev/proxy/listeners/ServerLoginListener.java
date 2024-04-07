package us.whitedev.proxy.listeners;

import org.spacehq.mc.protocol.ServerLoginHandler;
import org.spacehq.mc.protocol.data.game.values.entity.player.GameMode;
import org.spacehq.mc.protocol.data.game.values.setting.Difficulty;
import org.spacehq.mc.protocol.data.game.values.world.WorldType;
import org.spacehq.mc.protocol.packet.ingame.server.ServerJoinGamePacket;
import org.spacehq.mc.protocol.packet.ingame.server.entity.player.ServerPlayerAbilitiesPacket;
import org.spacehq.mc.protocol.packet.ingame.server.entity.player.ServerPlayerPositionRotationPacket;
import org.spacehq.packetlib.Session;
import us.whitedev.proxy.function.SessionConnector;


public class ServerLoginListener implements ServerLoginHandler {
    @Override
    public void loggedIn(final Session session) {
        session.send(new ServerJoinGamePacket(0, false, GameMode.SPECTATOR, 1, Difficulty.NORMAL, 0, WorldType.DEFAULT, false));
        session.send(new ServerPlayerPositionRotationPacket(0, 0, 0, 0F, 0F));
        session.send(new ServerPlayerAbilitiesPacket(true, true, true, true, 0F, 0F));
        session.addListener(new ClientTransferListener());
        new SessionConnector(session).connectUser();
    }
}