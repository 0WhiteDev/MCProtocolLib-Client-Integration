package client.whitedev.mods.botter.functions;

import client.whitedev.mods.botter.telnet.Channels;
import client.whitedev.mods.botter.telnet.TelnetInit;

public class ChatMessage {

    private final TelnetInit telnetInit = TelnetInit.getInstance();
    private static String[] messagearray = new String[45];
    private static ChatMessage chatMessage;

    public static ChatMessage getInstance(){
        if(chatMessage == null){
            chatMessage = new ChatMessage();
        }
        return chatMessage;
    }

    public void sendChatMessage(String msg){
        telnetInit.getOut().println(Channels.CHAT + msg);
    }

    public void addToMessageArray(String msg){
        int firstEmptyIndex = -1;
        for (int i = 0; i < messagearray.length; i++) {
            if (messagearray[i] == null) {
                firstEmptyIndex = i;
                break;
            }
        }

        if (firstEmptyIndex != -1) {
            messagearray[firstEmptyIndex] = msg;
        } else {
            String[] temp = new String[messagearray.length];
            if (temp.length - 1 >= 0) System.arraycopy(messagearray, 1, temp, 0, temp.length - 1);
            messagearray = temp;
            messagearray[messagearray.length - 1] = msg;
        }

    }

    public String[] getMessageArray(){
        return messagearray;
    }
}
