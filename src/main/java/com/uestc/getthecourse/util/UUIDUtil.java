package com.uestc.getthecourse.util;

import java.util.UUID;

public class UUIDUtil {
    public static String uuid() {
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        return uuid;
    }
}
