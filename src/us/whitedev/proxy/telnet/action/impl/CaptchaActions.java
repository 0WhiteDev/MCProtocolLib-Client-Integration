package us.whitedev.proxy.telnet.action.impl;

import us.whitedev.proxy.function.SendPacket;
import us.whitedev.proxy.helper.CaptchaHelper;
import us.whitedev.proxy.telnet.Channels;
import us.whitedev.proxy.telnet.action.Action;

public class CaptchaActions {
    public static class Captcha1Action implements Action {

        private final Channels channel = Channels.CAPTCHA1;

        @Override
        public void onAction(String[] args) {
            CaptchaHelper.captchaBypass1 = args[0].equals("true");
        }

        @Override
        public Channels getChannel() {
            return channel;
        }
    }

    public static class Captcha2Action implements Action {

        private final Channels channel = Channels.CAPTCHA2;

        @Override
        public void onAction(String[] args) {
            CaptchaHelper.captchaBypass2 = args[0].equals("true");
        }

        @Override
        public Channels getChannel() {
            return channel;
        }
    }
}
