package us.whitedev.proxy.listeners;

import org.spacehq.mc.auth.data.GameProfile;
import org.spacehq.mc.protocol.data.message.TextMessage;
import org.spacehq.mc.protocol.data.status.PlayerInfo;
import org.spacehq.mc.protocol.data.status.ServerStatusInfo;
import org.spacehq.mc.protocol.data.status.VersionInfo;
import org.spacehq.mc.protocol.data.status.handler.ServerInfoBuilder;
import org.spacehq.packetlib.Session;

public class ServerPingListener implements ServerInfoBuilder {

    @Override
    public ServerStatusInfo buildInfo(final Session session) {
        return new ServerStatusInfo(
                new VersionInfo("§7madeq x 0WhiteDev", 47),
                new PlayerInfo(
                        1,
                        0,
                        new GameProfile[0]
                ),
                new TextMessage("§cBotService Proxy Server §8- §7Created by §4§l0WhiteDev"),
                null
        );
    }
}
