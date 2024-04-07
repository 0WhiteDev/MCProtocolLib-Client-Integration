package us.whitedev.proxy;

import org.spacehq.mc.protocol.MinecraftProtocol;
import org.spacehq.packetlib.Server;
import org.spacehq.packetlib.tcp.TcpSessionFactory;
import us.whitedev.proxy.function.crashers.CrashManager;
import us.whitedev.proxy.function.crashers.impl.TestMethod;
import us.whitedev.proxy.listeners.ServerLoginListener;
import us.whitedev.proxy.listeners.ServerPingListener;
import us.whitedev.proxy.telnet.TelnetInit;


public class ServerInit {

    public static void main(String[] args) {
        final Server server = new Server("0.0.0.0", 1339, MinecraftProtocol.class, new TcpSessionFactory());
        server.bind(false);

        server.setGlobalFlag("compression-threshold", 256);
        server.setGlobalFlag("verify-users", false);
        server.setGlobalFlag("info-builder", new ServerPingListener());
        server.setGlobalFlag("login-handler", new ServerLoginListener());

        CrashManager.getManager().addMethod(
                new TestMethod()
        );

        TelnetInit.init();
    }
}
