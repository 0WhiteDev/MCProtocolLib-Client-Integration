package us.whitedev.proxy.telnet.action.impl.packets;

import org.spacehq.mc.protocol.data.game.values.window.ClickItemParam;
import org.spacehq.mc.protocol.data.game.values.window.WindowActionParam;
import us.whitedev.proxy.function.SendPacket;
import us.whitedev.proxy.telnet.Channels;
import us.whitedev.proxy.telnet.action.Action;

import java.util.Arrays;

public class WindowAction implements Action {

    private final Channels channel = Channels.WINDOW;

    @Override
    public void onAction(String[] args) {
        int windowId = Integer.parseInt(args[0]);
        int actionId = Integer.parseInt(args[4]);
        int slotId = Integer.parseInt(args[1]);
        WindowActionParam actionParam;
        int temp = Integer.parseInt(args[2]);
        if (temp == 1) {
            actionParam = ClickItemParam.RIGHT_CLICK;
        } else {
            actionParam = ClickItemParam.LEFT_CLICK;
        }
        SendPacket.sendClickWindowPacket(windowId, actionId, slotId, actionParam);
    }

    @Override
    public Channels getChannel() {
        return channel;
    }
}
