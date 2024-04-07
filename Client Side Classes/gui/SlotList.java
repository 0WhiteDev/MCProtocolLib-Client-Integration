package client.whitedev.mods.botter.gui;

import client.whitedev.helper.ChatHelper;
import client.whitedev.mods.botter.repository.UserRep;
import client.whitedev.mods.botter.telnet.Channels;
import client.whitedev.mods.botter.telnet.TelnetInit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiSlot;

import java.util.Arrays;

public class SlotList extends GuiSlot {

    private final UserRep userRep = UserRep.getInstance();
    private long lastClickTime = 0;
    private static final long CLICK_DELAY = 500;


    public SlotList(Minecraft mc, int width, int height, int top, int bottom) {
        super(mc, width, height, top, bottom, 15);
    }

    @Override
    protected int getSize() {
        return 100;
    }

    @Override
    protected void elementClicked(int i, boolean b, int i1, int i2) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastClickTime > CLICK_DELAY) {
            lastClickTime = currentTime;
            String online = userRep.getOnlineUsers()[i];
            if (online != null) {
                if (Arrays.asList(userRep.getSelectedSessions()).contains(online)) {
                    userRep.removeSelectedSession(online);
                    TelnetInit.getInstance().getOut().println(Channels.REMOVESELECT + online);
                } else {
                    userRep.addSelectedSession(online);
                    TelnetInit.getInstance().getOut().println(Channels.ADDSELECT + online);
                }
            }
        }
    }


    @Override
    protected boolean isSelected(int i) {
        return false;
    }

    @Override
    protected void drawBackground() {}

    @Override
    protected void drawSlot(int i, int i1, int i2, int i3, int i4, int i5) {
        int j = 0;
        String[] onlineUsers = userRep.getOnlineUsers();
        String[] selectedUsers = userRep.getSelectedSessions();

        for (String onlineUser : onlineUsers) {
            if (onlineUser != null) {
                boolean isSelected = false;
                for (String selectedUser : selectedUsers) {
                    if (selectedUser != null && selectedUser.equals(onlineUser)) {
                        isSelected = true;
                        break;
                    }
                }
                String formattedString = isSelected ? "&a&l" + onlineUser : onlineUser;
                Gui.drawCenteredString(mc.fontRendererObj, ChatHelper.fix(formattedString), width / 2, j * 10 + 20 + (j * 5), -1);
            }
            j++;
        }

    }

}
