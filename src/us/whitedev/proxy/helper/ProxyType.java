package us.whitedev.proxy.helper;

import java.net.Proxy;
import java.util.Objects;

public final class ProxyType {
    private final Proxy proxy;
    private final Type type;
    private static ProxySwitcher proxySwitcher = ProxySwitcher.NOPROXY;

    public static ProxySwitcher getSwitchedType(){
        return proxySwitcher;
    }

    public static void setProxySwitcher(ProxySwitcher proxyType){
        proxySwitcher = proxyType;
    }

    public ProxyType(Proxy proxy, Type type) {
        this.proxy = proxy;
        this.type = type;
    }

    public Proxy proxy() {
        return proxy;
    }

    public Type type() {
        return type;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        ProxyType that = (ProxyType) obj;
        return Objects.equals(this.proxy, that.proxy) &&
                Objects.equals(this.type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(proxy, type);
    }

    @Override
    public String toString() {
        return "ProxyType[" +
                "proxy=" + proxy + ", " +
                "type=" + type + ']';
    }

    public enum Type {
        SOCKS4(Proxy.Type.SOCKS),
        SOCKS5(Proxy.Type.SOCKS),
        HTTP(Proxy.Type.HTTP);

        private final Proxy.Type javaType;

        Type(final Proxy.Type javaType) {
            this.javaType = javaType;
        }

        public Proxy.Type getJavaType() {
            return this.javaType;
        }
    }
}
