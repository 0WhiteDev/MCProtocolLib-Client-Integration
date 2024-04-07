package client.whitedev.mods.botter.gui;

import client.whitedev.helper.ChatHelper;
import client.whitedev.mods.botter.functions.ChatMessage;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import java.awt.*;
import java.io.IOException;

public class GuiConsole extends GuiScreen {

    private ChatMessage chatMessage = ChatMessage.getInstance();

    public void initGui() {
        this.buttonList.add(new GuiButton(999, -25, 0, 98, 20, "< Back"));
    }

    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 999) {
            this.mc.displayGuiScreen(new GuiBotMenu());
        }
    }

    public void updateScreen() {
        super.updateScreen();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        drawRect(0, 0, width ,height, new Color(0xFFFFFF).getRGB());
        drawRect(1, 1, width - 1 ,height - 1, new Color(0).getRGB());
        drawCenteredString(this.fontRendererObj, ChatHelper.fix("&cBotService System &7- &fConsole (Bots output)"), width / 2, 8, -1);
        for (int i = 0; i < chatMessage.getMessageArray().length; i++) {
            if (chatMessage.getMessageArray()[i] != null) {
                drawString(this.fontRendererObj, ChatHelper.fix(chatMessage.getMessageArray()[i]), 4, 25 + (i * 10), -1);
            }
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}

