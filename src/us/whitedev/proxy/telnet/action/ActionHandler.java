package us.whitedev.proxy.telnet.action;

import us.whitedev.proxy.telnet.Channels;

import java.util.ArrayList;
import java.util.Arrays;

public class ActionHandler {

    private static ActionHandler actionHandler;

    public static ActionHandler getInstance(){
        if(actionHandler == null){
            actionHandler = new ActionHandler();
        }
        return actionHandler;
    }

    private final ArrayList<Action> actionList = new ArrayList<>();

    public void registerAction(Action... actions){
        Arrays.stream(actions).forEach(this::addActionToArray);
    }

    public void handleAction(Channels channel, String message){
        actionList.forEach(action -> {
            if(action.getChannel() == channel) {
                String[] data = message.replace(channel.name(), "").split(" ");
                action.onAction(data);
            }
        });
    }

    public ArrayList<Action> getActionList() {
        return actionList;
    }

    private void addActionToArray(Action action){
        this.actionList.add(action);
    }


}
