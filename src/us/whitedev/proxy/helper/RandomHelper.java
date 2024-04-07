package us.whitedev.proxy.helper;

import java.util.concurrent.ThreadLocalRandom;

public final class RandomHelper {

    private static final char[] CHARS = "aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ0123456789!@#$%^&*()_+-=[]{}|,<.>/?~".toCharArray();

    public static String getRandomChar() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 25; i++) {
            stringBuilder.append(CHARS[ThreadLocalRandom.current().nextInt(CHARS.length)]);
        }

        return stringBuilder.toString();
    }

    public static String getRandomChar(int length) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            stringBuilder.append(CHARS[ThreadLocalRandom.current().nextInt(CHARS.length)]);
        }

        return stringBuilder.toString();
    }

}