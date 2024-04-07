package us.whitedev.proxy.listeners;

import org.spacehq.mc.protocol.data.game.values.ClientRequest;
import org.spacehq.mc.protocol.packet.ingame.client.ClientChatPacket;
import org.spacehq.mc.protocol.packet.ingame.client.ClientKeepAlivePacket;
import org.spacehq.mc.protocol.packet.ingame.client.ClientRequestPacket;
import org.spacehq.mc.protocol.packet.ingame.server.ServerChatPacket;
import org.spacehq.mc.protocol.packet.ingame.server.ServerKeepAlivePacket;
import org.spacehq.mc.protocol.packet.ingame.server.ServerTitlePacket;
import org.spacehq.mc.protocol.packet.ingame.server.entity.*;
import org.spacehq.mc.protocol.packet.ingame.server.entity.player.ServerUpdateHealthPacket;
import org.spacehq.mc.protocol.packet.ingame.server.world.ServerPlaySoundPacket;
import org.spacehq.mc.protocol.packet.ingame.server.world.ServerUpdateTimePacket;
import org.spacehq.mc.protocol.packet.login.server.LoginSuccessPacket;
import org.spacehq.packetlib.Client;
import org.spacehq.packetlib.event.session.DisconnectedEvent;
import org.spacehq.packetlib.event.session.PacketReceivedEvent;
import org.spacehq.packetlib.event.session.PacketSentEvent;
import org.spacehq.packetlib.event.session.SessionAdapter;
import us.whitedev.proxy.helper.CaptchaHelper;
import us.whitedev.proxy.repository.SessionsRep;
import us.whitedev.proxy.telnet.Channels;
import us.whitedev.proxy.telnet.TelnetInit;
import us.whitedev.proxy.utils.Logger;


public class ClientListener extends SessionAdapter {
    private final String playerName;
    private int allReceived = 0;


    public ClientListener(String playerName) {
        this.playerName = playerName;
    }

    @Override
    public void packetSent(PacketSentEvent event) {
    }

    @Override
    public void packetReceived(PacketReceivedEvent event){
        if(allReceived > 100){
            allReceived = 0;
            event.getSession().send(new ClientKeepAlivePacket(0));
        }
        if(event.getPacket() instanceof LoginSuccessPacket){
            TelnetInit.sendMessageToUsers(Channels.JOIN.name() + playerName);
        }
        Client[] selectedUsers = SessionsRep.getInstance().getSelectedUsers();
        for (Client selectedUser : selectedUsers) {
            if(event.getPacket() instanceof ServerTitlePacket && selectedUser != null && selectedUser.getSession().equals(event.getSession())){
                if(CaptchaHelper.captchaBypass2 && selectedUser.getSession().equals(event.getSession())) {
                    String title1 = CaptchaHelper.extractCode(((ServerTitlePacket) event.getPacket()).getTitle().toString());
                    String subtitle1 = CaptchaHelper.extractCode(((ServerTitlePacket) event.getPacket()).getSubtitle().toString());
                    String action1 = CaptchaHelper.extractCode(((ServerTitlePacket) event.getPacket()).getAction().toString());
                    String title2 = CaptchaHelper.extractCodeFromRegister(((ServerTitlePacket) event.getPacket()).getTitle().toString());
                    String subtitle2 = CaptchaHelper.extractCodeFromRegister(((ServerTitlePacket) event.getPacket()).getSubtitle().toString());
                    String action2 = CaptchaHelper.extractCodeFromRegister(((ServerTitlePacket) event.getPacket()).getAction().toString());
                    if (title1 != null) {
                        event.getSession().send(new ClientChatPacket("/register Hasplo31 Hasplo31" + title1));
                    } else if (subtitle1 != null){
                        event.getSession().send(new ClientChatPacket("/register Hasplo31 Hasplo31" + subtitle1));
                    } else if (action1 != null){
                        event.getSession().send(new ClientChatPacket("/register Hasplo31 Hasplo31" + action1));
                    } else if (title2 != null) {
                        event.getSession().send(new ClientChatPacket(title2));
                    } else if (subtitle2 != null){
                        event.getSession().send(new ClientChatPacket(subtitle2));
                    } else if (action2 != null){
                        event.getSession().send(new ClientChatPacket(action2));
                    }
                }
            }
            if (event.getPacket() instanceof ServerChatPacket && selectedUser != null && selectedUser.getSession().equals(event.getSession())) {
                TelnetInit.sendMessageToUsers(Channels.CHAT.name() + "[" + playerName + "] " + ((ServerChatPacket) event.getPacket()).getMessage());
                if(CaptchaHelper.captchaBypass1 && selectedUser.getSession().equals(event.getSession())) {
                    String code1 = CaptchaHelper.extractCode(((ServerChatPacket) event.getPacket()).getMessage().toString());
                    String code2 = CaptchaHelper.extractCodeFromRegister(((ServerChatPacket) event.getPacket()).getMessage().toString());
                    if (code1 != null) {
                        event.getSession().send(new ClientChatPacket("/register Hasplo31 Hasplo31" + code1));
                    } else if (code2 != null){
                        event.getSession().send(new ClientChatPacket(code2));
                    }
                }
                break;
            }
        }
        if(event.getPacket() instanceof ServerEntityEquipmentPacket) {
            if (SessionsRep.getInstance().isInUsersWithItem(event.getSession())) {
                SessionsRep.getInstance().removeFromUsersWithItem(event.getSession());
            }
            if(((ServerEntityEquipmentPacket) event.getPacket()).getItem() != null)
                SessionsRep.getInstance().addToUsersWithItem(event.getSession(), ((ServerEntityEquipmentPacket) event.getPacket()).getItem());
        }

    }

    @Override
    public void disconnected(DisconnectedEvent event) {
        SessionsRep.getInstance().removeOnlineUser(playerName);
        TelnetInit.sendMessageToUsers(Channels.DISCONNECTED.name() + playerName);
        Logger.send(Logger.LogType.INFO, "Disconnected: " + event.getReason());
        if (event.getCause() != null) event.getCause().printStackTrace();
    }
}
