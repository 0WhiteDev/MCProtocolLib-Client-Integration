package client.whitedev.mods.botter.telnet;

import client.whitedev.helper.ChatHelper;
import client.whitedev.mods.botter.functions.ChatMessage;
import client.whitedev.mods.botter.handler.PacketsHandler;
import client.whitedev.mods.botter.handler.impl.*;
import client.whitedev.mods.botter.repository.MethodRep;
import client.whitedev.mods.botter.repository.UserRep;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TelnetInit {

    BufferedReader in;
    PrintWriter out;
    private static TelnetInit telnetInit;
    private UserRep user = UserRep.getInstance();
    private MethodRep methodRep = MethodRep.getInstance();

    public static TelnetInit getInstance() {
        if (telnetInit == null) {
            telnetInit = new TelnetInit();
        }
        return telnetInit;
    }

    public TelnetInit() {
        try {
            PacketsHandler.getInstance().registerPackets(
                    new AnimationPacket(),
                    new BlockPlacePacket(),
                    new CloseWindowPacket(),
                    new KeepAlivePacket(),
                    new LookPacket(),
                    new PositionLookPacket(),
                    new PositionPacket(),
                    new SlotPacket(),
                    new TransactionPacket(),
                    new WindowClickPacket()
            );
            run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedReader getIn() {
        return in;
    }

    public PrintWriter getOut() {
        return out;
    }

    private void run() throws IOException {

        Socket socket = new Socket("127.0.0.1", 8888);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        new Thread(() -> {
            String message;
            try {
                while ((message = in.readLine()) != null) {
                    if (message.startsWith(Channels.CHAT.name())) {
                        ChatMessage.getInstance().addToMessageArray(message.replace(Channels.CHAT.name(), ""));
                    }else if(message.startsWith(Channels.JOIN.name())){
                        user.addOnlienUser(message.replace(Channels.JOIN.name(), ""));
                    }else if(message.startsWith(Channels.DISCONNECTED.name())){
                        user.removeOnlineUser(message.replace(Channels.DISCONNECTED.name(), ""));
                    }else if(message.startsWith(Channels.MESSAGE.name())){
                        new ChatHelper().sendMessage(ChatHelper.fix(message.replace(Channels.MESSAGE.name(), "")), true);
                    }else if(message.startsWith(Channels.CRASHLIST.name())){
                        methodRep.addToMethod(message.replace(Channels.CRASHLIST.name(), ""));
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }).start();
    }

    public static void init() throws Exception {
        
    }

}
