package us.whitedev.proxy.utils;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    public static void send(LogType logType, String message){
        System.out.println("[" + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "] " + "[" + logType.name() + "] " + message);
    }

    public enum LogType{
        WARNING, ERROR, INFO, DEBUG
    }

}
