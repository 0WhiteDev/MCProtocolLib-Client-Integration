package us.whitedev.proxy.telnet.action.impl.packets;

import us.whitedev.proxy.function.SendPacket;
import us.whitedev.proxy.telnet.Channels;
import us.whitedev.proxy.telnet.action.Action;

public class PositionActions {
    public static class MoveAction implements Action{
        private final Channels channel = Channels.MOVE;

        @Override
        public void onAction(String[] args) {
            double x = Double.parseDouble(args[0]);
            double y = Double.parseDouble(args[1]);
            double z = Double.parseDouble(args[2]);
            boolean onGround;
            onGround = args[3].equals("true");
            SendPacket.sendMovePacket(onGround, x, y, z);
        }

        @Override
        public Channels getChannel() {
            return channel;
        }
    }

    public static class LookAction implements Action{
        private final Channels channel = Channels.LOOK;

        @Override
        public void onAction(String[] args) {
            float playerYaw = Float.parseFloat(args[0]);
            float playerPitch = Float.parseFloat(args[1]);
            boolean isOnGround;
            isOnGround = args[2].equals("true");
            SendPacket.sendPlayerLookPacket(playerYaw, playerPitch, isOnGround);
        }

        @Override
        public Channels getChannel() {
            return channel;
        }
    }

    public static class PosLookAction implements Action{
        private final Channels channel = Channels.POSLOOK;

        @Override
        public void onAction(String[] args) {
            double x = Double.parseDouble(args[0]);
            double y = Double.parseDouble(args[1]);
            double z = Double.parseDouble(args[2]);
            float yaw = Float.parseFloat(args[3]);
            float pitch = Float.parseFloat(args[4]);
            boolean isOnGround;
            isOnGround = args[5].equals("true");
            SendPacket.sendPlayerPosLookPacket(isOnGround, x, y, z, yaw, pitch);
        }

        @Override
        public Channels getChannel() {
            return channel;
        }
    }
}