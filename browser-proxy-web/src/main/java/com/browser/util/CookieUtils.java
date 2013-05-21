package com.browser.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author aladdin
 */
public class CookieUtils {
    
    public static Map<String, String> parseCookie(String cookies) {
        String[] cookieArr = cookies.split("; ");
        Map<String, String> cookieMap = new HashMap<String, String>(cookieArr.length, 1);
        String[] arr;
        for (String cookie : cookieArr) {
            arr = cookie.split("=");
            cookieMap.put(arr[0], arr[1]);
        }
        return cookieMap;
    }
    
    public static String createCookie(Map<String, String> cookieMap) {
        StringBuilder cookieBuilder = new StringBuilder(512);
        Set<Map.Entry<String, String>> entrySet = cookieMap.entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            cookieBuilder.append(entry.getKey()).append('=').append(entry.getValue()).append("; ");
        }
        String cookie = cookieBuilder.toString();
        return cookie;
    }
}
