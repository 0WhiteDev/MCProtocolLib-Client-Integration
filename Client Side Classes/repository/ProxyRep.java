package client.whitedev.mods.botter.repository;

public class ProxyRep {
    private static ProxyRep proxyRep;
    private ProxyType type = ProxyType.NOPROXY;

    public static ProxyRep getInstance(){
        if(proxyRep == null){
            proxyRep = new ProxyRep();
        }
        return proxyRep;
    }

    public ProxyType getType(){
        return type;
    }

    public void setType(ProxyType type){
        this.type = type;
    }

}
