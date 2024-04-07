package us.whitedev.proxy.helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CaptchaHelper {

    public static boolean captchaBypass1 = false;
    public static boolean captchaBypass2 = false;

    public static String extractCode(String text) {
        int colonIndex = ignoreTextFormation(text).indexOf(":");
        if (colonIndex != -1) {
            String afterColon = ignoreTextFormation(text).substring(colonIndex + 1).trim();
            String[] result = afterColon.split("\\s+");
            if (result.length > 0) {
                return result[0];
            }
        }
        return null;
    }

    public static String extractCodeFromRegister(String text) {
        String result = "";
        String regexPattern = "/register" + "\\s(\\S+\\s){0," + 2 + "}\\S+";
        Pattern pattern = Pattern.compile(regexPattern);
        Matcher matcher = pattern.matcher(ignoreTextFormation(text));
        if (matcher.find()) {
            result = matcher.group();
        }
        if(!result.equals("")) {
            String[] splited = result.split(" ");
            if (splited[1].equals(splited[2])) {
                result = "/register Hamaslo001 Hamaslo001 " + splited[3];
            } else {
                result = "/register Hamaslo001 Hamaslo001 " + splited[2];
            }
            return result;
        }
        return null;
    }

    private static String ignoreTextFormation(String text) {
        String formationChars = "[&§].";
        Pattern pattern = Pattern.compile(formationChars);
        Matcher matcher = pattern.matcher(text);
        return matcher.replaceAll("");
    }
}
