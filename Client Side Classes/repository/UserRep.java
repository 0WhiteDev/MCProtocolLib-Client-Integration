package client.whitedev.mods.botter.repository;

public class UserRep {
    private static UserRep userRep;
    private boolean isRandomNickname = false;
    private boolean isCaptchaBypass1 = false;
    private boolean isCaptchaBypass2 = false;
    private boolean isCaptchaBypass3 = false;
    private boolean botMovement = true;
    private String[] onlineUsers = new String[100];
    private String[] selectedSessions = new String[100];
    private int allConnectedSessions = 0;

    public static UserRep getInstance(){
        if(userRep == null){
            userRep = new UserRep();
        }
        return userRep;
    }

    public boolean isBotMovement() {
        return botMovement;
    }

    public void setBotMovement(boolean botMovement) {
        this.botMovement = botMovement;
    }

    public boolean isCaptchaBypass1() {
        return isCaptchaBypass1;
    }

    public void setCaptchaBypass1(boolean captchaBypass1) {
        isCaptchaBypass1 = captchaBypass1;
    }

    public boolean isCaptchaBypass2() {
        return isCaptchaBypass2;
    }

    public void setCaptchaBypass2(boolean captchaBypass2) {
        isCaptchaBypass2 = captchaBypass2;
    }

    public boolean isCaptchaBypass3() {
        return isCaptchaBypass3;
    }

    public void setCaptchaBypass3(boolean captchaBypass3) {
        isCaptchaBypass3 = captchaBypass3;
    }

    public int getAllConnectedSessions() {
        if(allConnectedSessions <= 0){
            allConnectedSessions = 0;
        }
        return allConnectedSessions;
    }

    public void setAllConnectedSessions(int allConnectedSessions) {
        this.allConnectedSessions = allConnectedSessions;
    }

    public boolean isRandomNickname() {
        return isRandomNickname;
    }

    public void setRandomNickname(boolean randomNickname) {
        isRandomNickname = randomNickname;
    }

    public void addOnlienUser(String name){
        setAllConnectedSessions(getAllConnectedSessions() + 1);
        for (int i = 0; i < onlineUsers.length; i++) {
            if(onlineUsers[i] == null)  {
                onlineUsers[i] = name;
                break;
            }
        }
    }

    public void removeOnlineUser(String name) {
        setAllConnectedSessions(getAllConnectedSessions() - 1);
        for (int i = 0; i < onlineUsers.length; i++) {
            if (onlineUsers[i] != null) {
                if (onlineUsers[i].equals(name)) {
                    onlineUsers[i] = null;
                    for (int j = i; j < onlineUsers.length - 1; j++) {
                        onlineUsers[j] = onlineUsers[j + 1];
                    }
                    onlineUsers[onlineUsers.length - 1] = null;
                    break;
                }
            }
        }
    }

    public String[] getOnlineUsers() {
        return onlineUsers;
    }

    public void addSelectedSession(String name){
        for (int i = 0; i < selectedSessions.length; i++) {
            if(selectedSessions[i] == null)  {
                selectedSessions[i] = name;
                break;
            }
        }
    }

    public void removeSelectedSession(String name) {
        for (int i = 0; i < selectedSessions.length; i++) {
            if (selectedSessions[i].equals(name)) {
                selectedSessions[i] = null;
                for (int j = i; j < selectedSessions.length - 1; j++) {
                    selectedSessions[j] = selectedSessions[j + 1];
                }
                selectedSessions[selectedSessions.length - 1] = null;
                break;
            }
        }
    }

    public String[] getSelectedSessions() {
        return selectedSessions;
    }

    public boolean isInSelectedSessions(String onlineSession) {
        for (String session : getSelectedSessions()) {
            if (session != null && session.equals(onlineSession)) {
                return true;
            }
        }
        return false;
    }

}
