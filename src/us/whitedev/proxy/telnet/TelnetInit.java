package us.whitedev.proxy.telnet;

import us.whitedev.proxy.telnet.action.ActionHandler;
import us.whitedev.proxy.telnet.action.impl.*;
import us.whitedev.proxy.telnet.action.impl.packets.*;
import us.whitedev.proxy.utils.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

public class TelnetInit {
    private final ActionHandler actionHandler = ActionHandler.getInstance();
    static ArrayList<PrintWriter> clientOutputStreams;
    static int PORT;

    public static void init() {
        PORT = 8888;
        new TelnetInit().runServer();
    }

    public void runServer() {
        Logger.send(Logger.LogType.INFO,"Running Server...");
        Logger.send(Logger.LogType.INFO,"Registering actions...");
        clientOutputStreams = new ArrayList<>();
        actionHandler.registerAction(
                new ArmAction(),
                new BlockPlaceAction(),
                new ChatAction(),
                new KeepAliveAction(),
                new PositionActions.MoveAction(),
                new PositionActions.LookAction(),
                new PositionActions.PosLookAction(),
                new RespawnAction(),
                new SlotAction(),
                new TransactionAction(),
                new WindowAction(),
                new WindowCloseAction(),
                new AddSelectAction(),
                new AlteningJoinAction(),
                new CaptchaActions.Captcha1Action(),
                new CaptchaActions.Captcha2Action(),
                new ClientAction(),
                new CrashAction(),
                new DisconnectAction(),
                new EasymcJoinAction(),
                new JoinAction(),
                new PremiumJoinAction(),
                new ProxyAction(),
                new RemoveSelectAction(),
                new SessionConnectAction()
        );
        Logger.send(Logger.LogType.INFO,"Actions registered successful!");
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            Logger.send(Logger.LogType.INFO,"Server started on port " + PORT);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
                clientOutputStreams.add(writer);

                Thread thread = new Thread(new ClientHandler(clientSocket));
                thread.start();
            }
        } catch (Exception ex) {
            Logger.send(Logger.LogType.ERROR,"An error was detected while creating/running the server with error " + ex.getMessage() + ", please check stacktrace!");
            ex.printStackTrace();
        }
    }

    public static void sendMessageToUsers(String message) {
        Iterator<PrintWriter> it = clientOutputStreams.iterator();
        while (it.hasNext()) {
            try {
                PrintWriter writer = it.next();
                writer.println(message);
                writer.flush();
            } catch (Exception ex) {
                Logger.send(Logger.LogType.ERROR,"An error was detected while sending message to user with error " + ex.getMessage() + ", please check stacktrace!");
                ex.printStackTrace();
            }
        }
    }
}

class ClientHandler implements Runnable {
    private final ActionHandler actionHandler = ActionHandler.getInstance();
    private BufferedReader reader;

    public ClientHandler(Socket clientSocket) {
        try {
            InputStreamReader isReader = new InputStreamReader(clientSocket.getInputStream());
            reader = new BufferedReader(isReader);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void run() {
        String message;
        try {
            while ((message = reader.readLine()) != null) {
                for(Channels channel : Channels.values()){
                    if(message.startsWith(channel.name())){
                        Logger.send(Logger.LogType.DEBUG,"A packet was received from the user -> " + channel.name());
                        actionHandler.handleAction(channel, message);
                    }
                }
            }
        } catch (Exception ex) {
            Logger.send(Logger.LogType.INFO,"Client disconnected from the proxy server -> " + ex.getMessage());
        }
    }
}
