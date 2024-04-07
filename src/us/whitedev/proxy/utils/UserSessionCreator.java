package us.whitedev.proxy.utils;

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
import org.spacehq.packetlib.tcp.TcpSessionFactory;
import us.whitedev.proxy.helper.ProxyType;
import us.whitedev.proxy.repository.SessionsRep;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.util.UUID;

public class UserSessionCreator {
    private static TheAlteningAuthentication serviceSwitch = TheAlteningAuthentication.mojang();
    public static String JOINAUTH = "https://sessionserver.mojang.com/session/minecraft/join";

    public static TcpSessionFactory getTcpSessionFactory(final Proxy proxy) {
        switch (ProxyType.getSwitchedType()){
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

    public void createNewSession(String name, String host, int port, Proxy proxy) {
        JOINAUTH = "https://sessionserver.mojang.com/session/minecraft/join";
        MinecraftProtocol protocol = new MinecraftProtocol(name);
        Client client = new Client(host, port, protocol, getTcpSessionFactory(proxy));
        client.getSession().connect();

        SessionsRep.getInstance().addOnlineUsers(name, client);
    }

    public void createNewPremiumSession(String name, String password, String host, int port, Proxy proxy) {
        JOINAUTH = "https://sessionserver.mojang.com/session/minecraft/join";
        MicrosoftAuthResult result;
        MicrosoftAuthenticator authenticator = new MicrosoftAuthenticator();
        try {
            result = authenticator.loginWithCredentials(name, password);
        } catch (MicrosoftAuthenticationException e) {
            throw new RuntimeException(e);
        }
        String uuid = result.getProfile().getId();
        BigInteger uuidGen0 = new BigInteger(uuid.substring(0, 16), 16);
        BigInteger uuidGen1 = new BigInteger(uuid.substring(16, 32), 16);
        UUID parsedUuid = new UUID(uuidGen0.longValue(), uuidGen1.longValue());
        GameProfile gameProfile = new GameProfile(parsedUuid, result.getProfile().getName());
        MinecraftProtocol protocol = new MinecraftProtocol(gameProfile, result.getAccessToken());
        Client client = new Client(host, port, protocol, getTcpSessionFactory(proxy));
        client.getSession().connect();

        SessionsRep.getInstance().addOnlineUsers(name, client);
    }

    public void createNewAlteningSession(String name, String host, int port, Proxy proxy) {
        JOINAUTH = "http://sessionserver.thealtening.com/session/minecraft/join";
        serviceSwitch.updateService(AlteningServiceType.THEALTENING);
        YggdrasilAuthenticationService service = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
        YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication) service.createUserAuthentication(Agent.MINECRAFT);
        auth.setUsername(name);
        auth.setPassword(name);
        try {
            auth.logIn();
            GameProfile gameProfile = new GameProfile(auth.getSelectedProfile().getId(), auth.getSelectedProfile().getName());
            MinecraftProtocol protocol = new MinecraftProtocol(gameProfile, auth.getAuthenticatedToken());
            Client client = new Client(host, port, protocol, getTcpSessionFactory(proxy));
            client.getSession().connect();

            SessionsRep.getInstance().addOnlineUsers(name, client);
            serviceSwitch.updateService(AlteningServiceType.MOJANG);
        } catch (com.mojang.authlib.exceptions.AuthenticationException var6) {
            var6.printStackTrace();
        }
    }

    public void createNewEasyMCSession(String token, String host, int port, Proxy proxy) {
        JOINAUTH = "https://sessionserver.easymc.io/session/minecraft/join";
        String[] data = connectToEasyMC(token);
        GameProfile gameProfile = new GameProfile(UUID.fromString(data[1]), data[0]);
        MinecraftProtocol protocol = new MinecraftProtocol(gameProfile, data[2]);
        Client client = new Client(host, port, protocol, getTcpSessionFactory(proxy));
        client.getSession().connect();

        SessionsRep.getInstance().addOnlineUsers(token, client);
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
