package us.whitedev.proxy.repository;

import org.spacehq.mc.protocol.data.game.ItemStack;
import org.spacehq.packetlib.Client;
import org.spacehq.packetlib.Session;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionsRep {
    private HashMap<String, Client> onlineUsers = new HashMap<>();
    private Client[] selectedUsers = new Client[100];
    private Map<Session, ItemStack> usersWithItem = new ConcurrentHashMap<>();
    private static SessionsRep sessionsRep;

    public static SessionsRep getInstance(){
        if(sessionsRep == null){
            sessionsRep = new SessionsRep();
        }
        return sessionsRep;
    }

    public void addToUsersWithItem(Session session, ItemStack item) {
        usersWithItem.put(session, item);
    }

    public boolean isInUsersWithItem(Session session) {
        return usersWithItem.containsKey(session);
    }

    public void removeFromUsersWithItem(Session session) {
        usersWithItem.remove(session);
    }

    public Map<Session, ItemStack> getUsersWithItem() {
        return usersWithItem;
    }


    public HashMap<String, Client> getOnlineUsers() {
        return onlineUsers;
    }

    public void addOnlineUsers(String name, Client client) {
        this.onlineUsers.put(name, client);
    }

    public void removeOnlineUser(String name){
        this.onlineUsers.remove(name);
    }

    public Client[] getSelectedUsers() {
        return selectedUsers;
    }

    public void addSelectedUser(Client client){
        for (int i = 0; i < selectedUsers.length; i++) {
            if(selectedUsers[i] == null)  {
                selectedUsers[i] = client;
                break;
            }
        }
    }

    public void removeSelectedUser(Client client){
        for (int i = 0; i < selectedUsers.length; i++) {
            if (selectedUsers[i] != null) {
                if (selectedUsers[i].equals(client)) {
                    selectedUsers[i] = null;
                    for (int j = i; j < selectedUsers.length - 1; j++) {
                        selectedUsers[j] = selectedUsers[j + 1];
                    }
                    selectedUsers[selectedUsers.length - 1] = null;
                    break;
                }
            }
        }
    }

}
