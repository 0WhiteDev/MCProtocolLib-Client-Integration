package us.whitedev.proxy.function;

import com.mojang.authlib.Agent;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import com.thealtening.auth.TheAlteningAuthentication;
import com.thealtening.auth.service.AlteningServiceType;
import fr.litarvan.openauth.microsoft.MicrosoftAuthResult;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticationException;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticator;
import org.json.JSONObject;
import org.spacehq.mc.auth.data.GameProfile;
import org.spacehq.mc.protocol.MinecraftProtocol;
import org.spacehq.packetlib.Client;
import org.spacehq.packetlib.ProxyInfo;
import org.spacehq.packetlib.Session;
import org.spacehq.packetlib.tcp.TcpSessionFactory;
import us.whitedev.proxy.helper.ProxySwitcher;
import us.whitedev.proxy.helper.ProxyType;
import us.whitedev.proxy.listeners.ServerTransferListener;
import us.whitedev.proxy.managers.ProxyManager;
import us.whitedev.proxy.repository.ClientRep;
import us.whitedev.proxy.repository.SessionsRep;
import us.whitedev.proxy.repository.UserRep;
import us.whitedev.proxy.utils.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.util.UUID;

public class SessionConnector {
    private final UserRep userRep = UserRep.getInstance();
    private final ClientRep clientRep = ClientRep.getInstance();
    private static final TheAlteningAuthentication serviceSwitch = TheAlteningAuthentication.mojang();
    private final ProxyManager proxyManager = ProxyManager.INST;
    private final Session session;

    public SessionConnector(Session session) {
        this.session = session;
    }

    public void connectUser() {
        Proxy proxy = getProxyType();
        new Thread(() -> {
            if (userRep.getServerIp() != null && userRep.getUsername() != null) {
                if (userRep.getUsername().contains("@alt.com")) {
                    createNewAlteningSession(proxy);
                } else if (userRep.getUsername().contains("@")) {
                    createNewPremiumSession(proxy);
                } else if (userRep.getUsername().length() == 20) {
                    createNewEasyMCSession(proxy);
                } else {
                    createNewSession();
                }
            }
        }).start();
    }

    private Proxy getProxyType(){
        Proxy proxy = null;
        if(!ProxyType.getSwitchedType().equals(ProxySwitcher.NOPROXY)) {
            proxy = proxyManager.random().proxy();
        }
        return proxy;
    }

    private static TcpSessionFactory getTcpSessionFactory(final Proxy proxy) {
        switch (ProxyType.getSwitchedType()) {
            case NOPROXY:
                return new TcpSessionFactory();
            case SOCKS4:
                return new TcpSessionFactory(new ProxyInfo(ProxyInfo.Type.SOCKS4, proxy.address()));
            case SOCKS5:
                return new TcpSessionFactory(new ProxyInfo(ProxyInfo.Type.SOCKS5, proxy.address()));
            case HTTP:
                return new TcpSessionFactory(new ProxyInfo(ProxyInfo.Type.HTTP, proxy.address()));
        }
        return new TcpSessionFactory();
    }

    private void createNewSession() {
        SessionAction.JOINAUTH = "https://sessionserver.mojang.com/session/minecraft/join";
        MinecraftProtocol protocol = new MinecraftProtocol(userRep.getUsername());
        Client client = new Client(userRep.getServerIp(), userRep.getServerPort(), protocol, new TcpSessionFactory());
        clientRep.setClient(client);
        client.getSession().connect();
        client.getSession().addListener(new ServerTransferListener(session, client));
        SessionsRep.getInstance().addOnlineUsers(userRep.getUsername(), client);
    }

    private void createNewPremiumSession(Proxy proxy) {
        SessionAction.JOINAUTH = "https://sessionserver.mojang.com/session/minecraft/join";
        MicrosoftAuthResult result;
        MicrosoftAuthenticator authenticator = new MicrosoftAuthenticator();
        try {
            result = authenticator.loginWithCredentials(userRep.getUsername(), userRep.getPassword());
        } catch (MicrosoftAuthenticationException e) {
            throw new RuntimeException(e);
        }
        String uuid = result.getProfile().getId();
        BigInteger uuidGen0 = new BigInteger(uuid.substring(0, 16), 16);
        BigInteger uuidGen1 = new BigInteger(uuid.substring(16, 32), 16);
        UUID parsedUuid = new UUID(uuidGen0.longValue(), uuidGen1.longValue());
        GameProfile gameProfile = new GameProfile(parsedUuid, result.getProfile().getName());
        MinecraftProtocol protocol = new MinecraftProtocol(gameProfile, result.getAccessToken());
        Client client = new Client(userRep.getServerIp(), userRep.getServerPort(), protocol, getTcpSessionFactory(proxy));
        clientRep.setClient(client);
        client.getSession().connect();
        client.getSession().addListener(new ServerTransferListener(session, client));
        SessionsRep.getInstance().addOnlineUsers(userRep.getUsername(), client);
    }

    private void createNewAlteningSession(Proxy proxy) {
        SessionAction.JOINAUTH = "http://sessionserver.thealtening.com/session/minecraft/join";
        serviceSwitch.updateService(AlteningServiceType.THEALTENING);
        YggdrasilAuthenticationService service = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
        YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication) service.createUserAuthentication(Agent.MINECRAFT);
        auth.setUsername(userRep.getUsername());
        auth.setPassword(userRep.getPassword());
        try {
            auth.logIn();
            GameProfile gameProfile = new GameProfile(auth.getSelectedProfile().getId(), auth.getSelectedProfile().getName());
            MinecraftProtocol protocol = new MinecraftProtocol(gameProfile, auth.getAuthenticatedToken());
            Client client = new Client(userRep.getServerIp(), userRep.getServerPort(), protocol, getTcpSessionFactory(proxy));
            clientRep.setClient(client);
            client.getSession().connect();
            client.getSession().addListener(new ServerTransferListener(session, client));
            SessionsRep.getInstance().addOnlineUsers(userRep.getUsername(), client);
            serviceSwitch.updateService(AlteningServiceType.MOJANG);
        } catch (com.mojang.authlib.exceptions.AuthenticationException var6) {
            var6.printStackTrace();
        }
    }

    private void createNewEasyMCSession(Proxy proxy) {
        SessionAction.JOINAUTH = "https://sessionserver.easymc.io/session/minecraft/join";
        String[] data = connectToEasyMC(userRep.getUsername());
        GameProfile gameProfile = new GameProfile(UUID.fromString(data[1]), data[0]);
        MinecraftProtocol protocol = new MinecraftProtocol(gameProfile, data[2]);
        Client client = new Client(userRep.getServerIp(), userRep.getServerPort(), protocol, getTcpSessionFactory(proxy));
        clientRep.setClient(client);
        client.getSession().connect();
        client.getSession().addListener(new ServerTransferListener(session, client));
        SessionsRep.getInstance().addOnlineUsers(userRep.getUsername(), client);
    }

    private String[] connectToEasyMC(String token) {
        String apiUrl = "https://api.easymc.io/v1/token/redeem";
        String mcName = null;
        String uuid = null;
        String session = null;
        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("token", token);
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            OutputStream os = conn.getOutputStream();
            os.write(requestBody.toString().getBytes());
            os.flush();
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line);
                }
                br.close();
                JSONObject jsonResponse = new JSONObject(response.toString());
                mcName = jsonResponse.getString("mcName");
                uuid = jsonResponse.getString("uuid");
                session = jsonResponse.getString("session");
            } else {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                StringBuilder errorResponse = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    errorResponse.append(line);
                }
                br.close();
                JSONObject errorJson = new JSONObject(errorResponse.toString());
                String errorMessage = errorJson.getString("error");
                Logger.send(Logger.LogType.ERROR, errorMessage);
            }
            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] data = new String[3];
        data[0] = mcName;
        data[1] = uuid;
        data[2] = session;
        return data;
    }

}
