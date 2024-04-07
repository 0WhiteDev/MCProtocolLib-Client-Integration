package us.whitedev.proxy.telnet.action.impl.packets;

import us.whitedev.proxy.function.ChatMessage;
import us.whitedev.proxy.telnet.Channels;
import us.whitedev.proxy.telnet.action.Action;

import java.util.Arrays;

public class ChatAction implements Action {

    private final Channels channel = Channels.CHAT;

    @Override
    public void onAction(String[] args) {
        StringBuilder message = new StringBuilder();
        Arrays.stream(args).forEach(arg -> message.append(arg).append(" "));
        ChatMessage.sendChatMessage(message.toString());
    }

    @Override
    public Channels getChannel() {
        return channel;
    }
}
