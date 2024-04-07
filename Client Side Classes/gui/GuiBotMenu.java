package client.whitedev.mods.botter.gui;

import client.whitedev.helper.ChatHelper;
import client.whitedev.mods.botter.functions.ChatMessage;
import client.whitedev.mods.botter.functions.RespawnBot;
import client.whitedev.mods.botter.functions.SendCrashPacket;
import client.whitedev.mods.botter.functions.connection.DisconnectBot;
import client.whitedev.mods.botter.functions.connection.JoinBot;
import client.whitedev.mods.botter.functions.connection.SessionConnector;
import client.whitedev.mods.botter.repository.MethodRep;
import client.whitedev.mods.botter.repository.ProxyRep;
import client.whitedev.mods.botter.repository.ProxyType;
import client.whitedev.mods.botter.repository.UserRep;
import client.whitedev.mods.botter.telnet.Channels;
import client.whitedev.mods.botter.telnet.TelnetInit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

import java.awt.*;
import java.io.IOException;

public class GuiBotMenu extends GuiScreen {

    private SlotList list;
    private UserRep userRep = UserRep.getInstance();
    private MethodRep methodRep = MethodRep.getInstance();
    private GuiTextField chatMessage, nickname, password, packets;
    private final FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
    private final ProxyRep proxyRep = ProxyRep.getInstance();


    public void initGui() {
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(999, width / 2 - 250, 105, 98, 20, ChatHelper.fix("&aConnect Bot")));
        this.buttonList.add(new GuiButton(992, width / 2 - 150, 105, 98, 20, ChatHelper.fix("&cDisconnect Bot")));
        this.buttonList.add(new GuiButton(991, width / 2 - 50, 105, 98, 20, ChatHelper.fix("&aMicrosoft Bot")));
        this.buttonList.add(new GuiButton(990, width / 2 + 50, 105, 98, 20, ChatHelper.fix("&aTheAltening Bot")));
        this.buttonList.add(new GuiButton(984, width / 2 + 150, 105, 98, 20, ChatHelper.fix("&aEasyMC Bot")));
        this.buttonList.add(new GuiButton(998, width / 2 - 48, 128, 98, 20, "Send CrashPacket"));
        this.buttonList.add(new GuiButton(989, width - 220, 0, 98, 20, ChatHelper.fix("&cDisconnect All")));
        this.buttonList.add(new GuiButton(997, 5, height  - 45, 98, 20, "Send ChatMessage"));
        this.buttonList.add(new GuiButton(996, -25, 0, 98, 20, "< Back"));
        this.buttonList.add(new GuiButton(995, width / 2 - 48, 150, 98, 20, ChatHelper.fix((userRep.isRandomNickname() ? "&a" : "&c") + "Random NickName")));
        this.buttonList.add(new GuiButton(994, width / 2 - 48, 185, 98, 20, ChatHelper.fix("Crash: &c" + methodRep.changeSelectedMethod())));
        this.buttonList.add(new GuiButton(993, width / 2 - 48, 275, 98, 20, "Bots Console (logs)"));
        this.buttonList.add(new GuiButton(987, width - 220, 20, 98, 20, ChatHelper.fix((userRep.isCaptchaBypass1() ? "&a" : "&c")) + "AutoCaptcha1"));
        this.buttonList.add(new GuiButton(986, width - 220, 40, 98, 20, ChatHelper.fix((userRep.isCaptchaBypass2() ? "&a" : "&c")) + "AutoCaptcha2"));
        this.buttonList.add(new GuiButton(985, width - 220, 60, 98, 20, ChatHelper.fix("Proxy Type: &c" + proxyRep.getType())));
        this.buttonList.add(new GuiButton(983, width - 220, 80, 98, 20, ChatHelper.fix(userRep.isBotMovement() ? "&a" : "&c") + "Bot Movement"));
        this.buttonList.add(new GuiButton(982, width - 220, 100, 98, 20, "Respawn Bots"));
        this.buttonList.add(new GuiButton(981, width - 220, 120, 98, 20, "Connect my session"));
        list = new SlotList(mc, width  * 2 - 115, height, 10, height - 10);

        this.chatMessage = new GuiTextField(100, fr, 2,height - 22, width, 20);
        this.nickname = new GuiTextField(100, fr, width / 2 - 165,80, 150, 20);
        this.password = new GuiTextField(100, fr, width / 2 + 20,80, 150, 20);
        this.packets = new GuiTextField(100, fr, width / 2 - 45,230, 90, 20);
    }

    protected void actionPerformed(GuiButton button) throws IOException {
        list.actionPerformed(button);

        switch (button.id) {
            case 999:
                if (nickname.getText() != null && !nickname.getText().equals("") || userRep.isRandomNickname()) {
                    JoinBot.getInstance().joinBot(nickname.getText());
                }
                break;
            case 998:
                if(packets.getText() != null && !packets.getText().equals("")) {
                    new SendCrashPacket().sendCrash(methodRep.getSelectedMethod(), packets.getText());
                }
                break;
            case 997:
                if (chatMessage.getText() != null && !chatMessage.getText().equals("")) {
                    new ChatMessage().sendChatMessage(chatMessage.getText());
                }
                break;
            case 996:
                this.mc.displayGuiScreen(null);
                this.mc.setIngameFocus();
                break;
            case 995:
                userRep.setRandomNickname(!userRep.isRandomNickname());
                button.displayString = ChatHelper.fix((userRep.isRandomNickname() ? "&a" : "&c") + "Random NickName");
                break;
            case 994:
                button.displayString = ChatHelper.fix("Crash: &c" + methodRep.changeSelectedMethod());
                break;
            case 993:
                this.mc.displayGuiScreen(new GuiConsole());
                break;
            case 992:
                for(String name : userRep.getSelectedSessions())
                    if(name != null)
                        new DisconnectBot().disconnect(name);
                break;
            case 991:
                if (nickname.getText() != null && !nickname.getText().equals("") && password.getText() != null && !password.getText().equals("")) {
                    JoinBot.getInstance().joinPremiumBot(nickname.getText(), password.getText());
                }
                break;
            case 990:
                if (nickname.getText() != null && !nickname.getText().equals("")) {
                    JoinBot.getInstance().joinAlteningBot(nickname.getText());
                }
                break;
            case 989:
                String[] onlineUsers = userRep.getOnlineUsers();
                StringBuilder valuesString = new StringBuilder();
                for (String value : onlineUsers) {
                    if (value != null) {
                        valuesString.append(value).append(" ");
                    }
                }
                String result = valuesString.toString().trim();
                new DisconnectBot().disconnect(result);
                break;
            case 987:
                userRep.setCaptchaBypass1(!userRep.isCaptchaBypass1());
                TelnetInit.getInstance().getOut().println(Channels.CAPTCHA1 + "" + userRep.isCaptchaBypass1());
                button.displayString = ChatHelper.fix((userRep.isCaptchaBypass1() ? "&a" : "&c")) + "AutoCaptcha1";
                break;
            case 986:
                userRep.setCaptchaBypass2(!userRep.isCaptchaBypass2());
                TelnetInit.getInstance().getOut().println(Channels.CAPTCHA2 + "" + userRep.isCaptchaBypass2());
                button.displayString = ChatHelper.fix((userRep.isCaptchaBypass1() ? "&a" : "&c")) + "AutoCaptcha2";
                break;
            case 985:
                switch (proxyRep.getType()){
                    case NOPROXY:
                        proxyRep.setType(ProxyType.SOCKS4);
                        break;
                    case SOCKS4:
                        proxyRep.setType(ProxyType.SOCKS5);
                        break;
                    case SOCKS5:
                        proxyRep.setType(ProxyType.HTTP);
                        break;
                    default:
                        proxyRep.setType(ProxyType.NOPROXY);
                        break;
                }
                TelnetInit.getInstance().getOut().println(Channels.PROXY + proxyRep.getType().name());
                button.displayString = ChatHelper.fix("Proxy Type: &c" + proxyRep.getType());
                break;
            case 984:
                if (nickname.getText() != null && !nickname.getText().equals("")) {
                    JoinBot.getInstance().joinEasymcBot(nickname.getText());
                }
                break;
            case 983:
                userRep.setBotMovement(!userRep.isBotMovement());
                button.displayString = ChatHelper.fix(userRep.isBotMovement() ? "&a" : "&c") + "Bot Movement";
                break;
            case 982:
                new RespawnBot().sendRespawnPacket();
                break;
            case 981:
                if(nickname.getText() != null) {
                    new SessionConnector().connectToProxyServer(mc.getCurrentServerData().serverIP, nickname.getText(), password.getText());
                }
                break;
        }
    }

    @Override
    public void handleMouseInput() throws IOException {
        list.handleMouseInput();
        super.handleMouseInput();
    }

    public void updateScreen() {
        super.updateScreen();
    }

    @Override
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        this.chatMessage.mouseClicked(mouseX, mouseY, mouseButton);
        this.nickname.mouseClicked(mouseX, mouseY, mouseButton);
        this.password.mouseClicked(mouseX, mouseY, mouseButton);
        this.packets.mouseClicked(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        this.chatMessage.textboxKeyTyped(typedChar, keyCode);
        this.nickname.textboxKeyTyped(typedChar, keyCode);
        this.password.textboxKeyTyped(typedChar, keyCode);
        this.packets.textboxKeyTyped(typedChar, keyCode);
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.chatMessage.drawTextBox();
        this.nickname.drawTextBox();
        this.password.drawTextBox();
        this.packets.drawTextBox();
        drawRect(width / 2 + 359, 0, width ,height, new Color(0xFFFFFF).getRGB());
        drawRect(width / 2 + 360, 0, width ,height, new Color(0).getRGB());
        drawCenteredString(this.fontRendererObj, ChatHelper.fix("&cBotService System &7- Connected Bots: (&a" + UserRep.getInstance().getAllConnectedSessions() + "&7)"), width / 2, 40, -1);
        drawCenteredString(this.fontRendererObj, ChatHelper.fix("&2Currently connected&7:"), width / 2 + 421, 4, -1);
        drawCenteredString(this.fontRendererObj, ChatHelper.fix("&7NickName/Email:"), width / 2 - 90 , 60, -1);
        drawCenteredString(this.fontRendererObj, ChatHelper.fix("&7Password:"), width / 2 + 90 , 60, -1);
        drawCenteredString(this.fontRendererObj, ChatHelper.fix("&7Crash Module Selector:"), width / 2, 175, -1);
        drawCenteredString(this.fontRendererObj, ChatHelper.fix("&7Packet Ammount:"), width / 2 , 210, -1);
        drawCenteredString(this.fontRendererObj, ChatHelper.fix("&7Bots Output:"), width / 2, 260, -1);
        list.drawScreen(mouseX, mouseY, partialTicks);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}

