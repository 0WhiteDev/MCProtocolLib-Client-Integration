package client.whitedev.mods.botter.functions.connection;

import client.whitedev.helper.RandomHelper;
import client.whitedev.mods.botter.repository.UserRep;
import client.whitedev.mods.botter.telnet.Channels;
import client.whitedev.mods.botter.telnet.TelnetInit;
import net.minecraft.client.Minecraft;

public class JoinBot {
    private final TelnetInit telnetInit = TelnetInit.getInstance();
    private static JoinBot joinBot;

    public static JoinBot getInstance(){
        if(joinBot == null){
            joinBot = new JoinBot();
        }
        return joinBot;
    }

    public void joinBot(String name){
        String nickname;
        if(UserRep.getInstance().isRandomNickname()){
            nickname = RandomHelper.getRandomHelper().getRandomString(RandomHelper.getRandomInt(6,12), false);
        }else {
            nickname = name + (UserRep.getInstance().getAllConnectedSessions() + 1);
        }
        String ipAddress = Minecraft.getMinecraft().getCurrentServerData().serverIP;
        String[] splitted = ipAddress.split(":");
        String host =(splitted[0]);
        int port = (splitted.length > 1 ? Integer.parseInt(splitted[1]) : 25565);
        telnetInit.getOut().println(Channels.JOIN + nickname + " " + host + " " + port);
    }

    public void joinPremiumBot(String name, String password){
        String ipAddress = Minecraft.getMinecraft().getCurrentServerData().serverIP;
        String[] splitted = ipAddress.split(":");
        String host =(splitted[0]);
        int port = (splitted.length > 1 ? Integer.parseInt(splitted[1]) : 25565);
        telnetInit.getOut().println(Channels.PREMIUMJOIN + name + " " + password + " " + host + " " + port);
    }

    public void joinAlteningBot(String name){
        String ipAddress = Minecraft.getMinecraft().getCurrentServerData().serverIP;
        String[] splitted = ipAddress.split(":");
        String host =(splitted[0]);
        int port = (splitted.length > 1 ? Integer.parseInt(splitted[1]) : 25565);
        telnetInit.getOut().println(Channels.ALTENINGJOIN + name + " " + host + " " + port);
    }

    public void joinEasymcBot(String name){
        String ipAddress = Minecraft.getMinecraft().getCurrentServerData().serverIP;
        String[] splitted = ipAddress.split(":");
        String host =(splitted[0]);
        int port = (splitted.length > 1 ? Integer.parseInt(splitted[1]) : 25565);
        telnetInit.getOut().println(Channels.EASYMC + name + " " + host + " " + port);
    }
}