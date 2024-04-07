package us.whitedev.proxy.repository;

public class UserRep {
    private static UserRep userConnectorUtil;
    private String serverIp;
    private int serverPort;
    private String username;
    private String password = "non-premium";

    public static UserRep getInstance(){
        if(userConnectorUtil == null){
            userConnectorUtil = new UserRep();
        }
        return userConnectorUtil;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
