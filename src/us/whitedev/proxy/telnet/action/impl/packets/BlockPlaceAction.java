package us.whitedev.proxy.telnet.action.impl.packets;

import org.spacehq.mc.protocol.data.game.Position;
import org.spacehq.mc.protocol.data.game.values.Face;
import us.whitedev.proxy.function.SendPacket;
import us.whitedev.proxy.telnet.Channels;
import us.whitedev.proxy.telnet.action.Action;

import java.util.Arrays;

public class BlockPlaceAction implements Action {

    private final Channels channel = Channels.BLOCKPLACE;

    @Override
    public void onAction(String[] args) {
        Position position = new Position(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
        Face face = Face.BOTTOM;
        float cursorX = Float.parseFloat(args[4]);
        float cursorY = Float.parseFloat(args[5]);
        float cursorZ = Float.parseFloat(args[6]);
        SendPacket.sendBlockPlacementPacket(position, face, cursorX, cursorY, cursorZ);
    }

    @Override
    public Channels getChannel() {
        return channel;
    }
}
