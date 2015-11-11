package com.example.daniel.qrcodecreator;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.util.List;

/**
 * Created by Daniel on 11/11/2015.
 */
public class MyWifiProperties {

    private String type,ssid;
   private Context context;

    public MyWifiProperties(Context context) {
        this.context = context;
        getProperties();

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    private void getProperties(){

        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        List<ScanResult> networkList = wifi.getScanResults();
        if (networkList != null) {
            for (ScanResult network : networkList)
            {
                String rawString =  network.capabilities;
                setSsid(network.SSID);
                Log.i("WIFI CONNECTION:  ", network.SSID + " capabilities : " + rawString);
                if(rawString.contains("nopass"))
                    setType("nopass");
                if(rawString.contains("WEP"))
                    setType("WEP");
                if(rawString.contains("WPA"))
                    setType("WPA");

            }
        }
    }

}
