package com.example.daniel.qrcodecreator.utils;

/**
 * Created by Daniel on 09/11/2015.
 */


enum NetworkType {


    WEP,
    WPA,
    NO_PASSWORD;


    static NetworkType forIntentValue(String networkTypeString) {
        if (networkTypeString == null) {
            return NO_PASSWORD;
        }
        if ("WPA".equals(networkTypeString)) {
            return WPA;
        }
        if ("WEP".equals(networkTypeString)) {
            return WEP;
        }
        if ("nopass".equals(networkTypeString)) {
            return NO_PASSWORD;
        }
        throw new IllegalArgumentException(networkTypeString);
    }


}