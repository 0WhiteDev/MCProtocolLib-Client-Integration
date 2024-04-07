package us.whitedev.proxy.telnet.action;

import us.whitedev.proxy.function.SessionAction;
import us.whitedev.proxy.repository.SessionsRep;
import us.whitedev.proxy.telnet.Channels;

public interface Action {
    void onAction(String[] args);
    Channels getChannel();
    SessionsRep session = SessionsRep.getInstance();
    SessionAction sessionAction = SessionAction.getInstance();
}
