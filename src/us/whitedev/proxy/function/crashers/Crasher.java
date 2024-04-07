package us.whitedev.proxy.function.crashers;

import org.spacehq.mc.protocol.data.game.ItemStack;
import us.whitedev.proxy.repository.SessionsRep;

public interface Crasher {

    SessionsRep session = SessionsRep.getInstance();

    String getName();
    boolean check(String string);
    void onMethod(String name, int packets);

    default ItemStack createItemStack(){ return null; };
}
