package us.whitedev.proxy.function;

import org.spacehq.mc.protocol.packet.ingame.client.ClientChatPacket;
import us.whitedev.proxy.repository.SessionsRep;

public class ChatMessage {

    private static SessionsRep sessionsRep = SessionsRep.getInstance();

    public static void sendChatMessage(String msg) {
        for (int j = 0; j < sessionsRep.getSelectedUsers().length; j++) {
            if (sessionsRep.getSelectedUsers()[j] != null) {
                sessionsRep.getSelectedUsers()[j].getSession().send(new ClientChatPacket(msg));
            }
        }
    }
}
