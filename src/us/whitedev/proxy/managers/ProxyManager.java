package us.whitedev.proxy.managers;

import us.whitedev.proxy.ServerInit;
import us.whitedev.proxy.helper.ProxyType;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProxyManager {

    public static final ProxyManager INST = new ProxyManager();

    private Set<String> alreadyLoaded = new HashSet<>();
    private List<ProxyType> proxies = new ArrayList<>();

    private int current = 0;

    private ProxyManager() {
    }

    public ProxyType random() {
        if (this.current >= this.proxies.size()) {
            this.current = 0;
        }

        return this.proxies.get(this.current++);
    }

    public List<ProxyType> getProxies() {
        return this.proxies;
    }

    public void loadProxies(final String fileName, final ProxyType.Type type) throws IOException {
        String filePath = ServerInit.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        File file = new File(filePath);
        String directoryPath = file.getParent();
        proxies = new ArrayList<>();
        alreadyLoaded = new HashSet<>();
        final Path path = Paths.get(directoryPath + "\\" + fileName);
        if (Files.notExists(path)) {
            System.err.println(path + " does not exist");
            return;
        }

        xd:
        for (final String rawAddress : Files.readAllLines(path)) {
            if (this.alreadyLoaded.contains(rawAddress)) {
                continue;
            }

            final String[] data = rawAddress.split(":", 3);
            if (data.length != 2) {
                continue;
            }

            final String ip = data[0];
            {
                final String[] ipParts = ip.split("\\.", 5);
                if (ipParts.length != 4) {
                    continue;
                }

                for (final String ipPart : ipParts) {
                    try {
                        final int parsed = Integer.parseInt(ipPart);
                        if (parsed < 0 || parsed > 255) {
                            continue xd;
                        }

                    } catch (final NumberFormatException exception) {
                        continue xd;
                    }
                }
            }

            final int port;
            try {
                port = Integer.parseInt(data[1]);
                if (port < 0 || port > 65535) {
                    continue;
                }

            } catch (final NumberFormatException exception) {
                continue;
            }

            this.alreadyLoaded.add(rawAddress);

            final InetSocketAddress address = new InetSocketAddress(ip, port);
            final Proxy proxy = new Proxy(type.getJavaType(), address);
            this.proxies.add(new ProxyType(proxy, type));
        }
    }
}
