package us.whitedev.proxy.function;

import org.spacehq.mc.protocol.data.game.Position;
import org.spacehq.mc.protocol.data.game.values.ClientRequest;
import org.spacehq.mc.protocol.data.game.values.Face;
import org.spacehq.mc.protocol.data.game.values.window.WindowAction;
import org.spacehq.mc.protocol.data.game.values.window.WindowActionParam;
import org.spacehq.mc.protocol.packet.ingame.client.ClientKeepAlivePacket;
import org.spacehq.mc.protocol.packet.ingame.client.ClientRequestPacket;
import org.spacehq.mc.protocol.packet.ingame.client.player.*;
import org.spacehq.mc.protocol.packet.ingame.client.window.ClientCloseWindowPacket;
import org.spacehq.mc.protocol.packet.ingame.client.window.ClientConfirmTransactionPacket;
import org.spacehq.mc.protocol.packet.ingame.client.window.ClientWindowActionPacket;
import org.spacehq.packetlib.Client;
import us.whitedev.proxy.repository.SessionsRep;

import java.util.Arrays;

public class SendPacket {

    private static SessionsRep sessionsRep = SessionsRep.getInstance();

    public static void sendMovePacket(boolean onGround, double x, double y, double z) {
        new Thread(() -> {
            try {
                for (int j = 0; j < sessionsRep.getSelectedUsers().length; j++) {
                    if (sessionsRep.getSelectedUsers()[j] != null) {
                        sessionsRep.getSelectedUsers()[j].getSession().send(new ClientPlayerPositionPacket(onGround, x, y, z));
                        Thread.sleep(200L);
                    }
                }
            } catch (Exception e) { e.printStackTrace(); }
        }).start();
    }

    public static void sendRespawnPacket() {
        new Thread(() -> {
            try {
                for (int j = 0; j < sessionsRep.getSelectedUsers().length; j++) {
                    if (sessionsRep.getSelectedUsers()[j] != null) {
                        sessionsRep.getSelectedUsers()[j].getSession().send(new ClientRequestPacket(ClientRequest.RESPAWN));
                        Thread.sleep(200L);
                    }
                }
            } catch (Exception e) { e.printStackTrace(); }
        }).start();
    }

    public static void sendPlayerLookPacket(float playerYaw, float playerPitch, boolean isOnGround) {
        for (int j = 0; j < sessionsRep.getSelectedUsers().length; j++) {
            if (sessionsRep.getSelectedUsers()[j] != null) {
                sessionsRep.getSelectedUsers()[j].getSession().send(new ClientPlayerRotationPacket(isOnGround, playerYaw, playerPitch));
            }
        }
    }

    public static void sendPlayerPosLookPacket(boolean onGround, double x, double y, double z, float yaw, float pitch) {
        new Thread(() -> {
            try {
                for (int j = 0; j < sessionsRep.getSelectedUsers().length; j++) {
                    if (sessionsRep.getSelectedUsers()[j] != null) {
                        sessionsRep.getSelectedUsers()[j].getSession().send(new ClientPlayerPositionRotationPacket(onGround, x, y, z, yaw, pitch));
                        Thread.sleep(200L);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void sendClickWindowPacket(int windowId, int actionId, int slot, WindowActionParam param) {
        for (int j = 0; j < sessionsRep.getSelectedUsers().length; j++) {
            if (sessionsRep.getSelectedUsers()[j] != null) {
                sessionsRep.getSelectedUsers()[j].getSession().send(new ClientWindowActionPacket(windowId, actionId, slot, null, WindowAction.CLICK_ITEM, param));
            }
        }
    }

    public static void sendBlockPlacementPacket(Position position, Face face, float cursorX, float cursorY, float cursorZ) {
        for (int j = 0; j < sessionsRep.getSelectedUsers().length; j++) {
            if (sessionsRep.getSelectedUsers()[j] != null) {
                sessionsRep.getSelectedUsers()[j].getSession().send(new ClientPlayerPlaceBlockPacket(position, face, sessionsRep.getUsersWithItem().get(sessionsRep.getSelectedUsers()[j].getSession()), cursorX, cursorY, cursorZ));
            }
        }
    }

    public static void sendArmAnimationPacket() {
        for (int j = 0; j < sessionsRep.getSelectedUsers().length; j++) {
            if (sessionsRep.getSelectedUsers()[j] != null) {
                sessionsRep.getSelectedUsers()[j].getSession().send(new ClientSwingArmPacket());
            }
        }
    }

    public static void sendKeepAlivePacket(int key) {
        for (int j = 0; j < sessionsRep.getSelectedUsers().length; j++) {
            if (sessionsRep.getSelectedUsers()[j] != null) {
                sessionsRep.getSelectedUsers()[j].getSession().send(new ClientKeepAlivePacket(key));
            }
        }
    }

    public static void sendChangeHeldItemPacket(int slot) {
        for (int j = 0; j < sessionsRep.getSelectedUsers().length; j++) {
            if (sessionsRep.getSelectedUsers()[j] != null) {
                sessionsRep.getSelectedUsers()[j].getSession().send(new ClientChangeHeldItemPacket(slot));
            }
        }
    }

    public static void sendWClosePacket(int windowid) {
        for (int j = 0; j < sessionsRep.getSelectedUsers().length; j++) {
            if (sessionsRep.getSelectedUsers()[j] != null) {
                sessionsRep.getSelectedUsers()[j].getSession().send(new ClientCloseWindowPacket(windowid));
            }
        }
    }

    public static void sendTransactionPacket(int windowid, int actionId, boolean accepted) {
        for (int j = 0; j < sessionsRep.getSelectedUsers().length; j++) {
            if (sessionsRep.getSelectedUsers()[j] != null) {
                sessionsRep.getSelectedUsers()[j].getSession().send(new ClientConfirmTransactionPacket(windowid, actionId, accepted));
            }
        }
    }
}
