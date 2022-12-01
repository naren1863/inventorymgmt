package com.synergy.invservice.util;

public class LogService {
    public static void logMessage(String message){
        System.out.println(new StringBuilder("---->>>>")
                .append(message)
                .append("\n")
        );
    }
}
