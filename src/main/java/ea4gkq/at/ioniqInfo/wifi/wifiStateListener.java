package ea4gkq.at.ioniqInfo.wifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import ea4gkq.at.ioniqInfo.MainActivity;

public class wifiStateListener extends BroadcastReceiver {
    private MainActivity mMainActivyty;
    @Override
    public void onReceive(Context context, Intent intent) {

        mMainActivyty = MainActivity.getInstace();
        if(mMainActivyty!=null) {
            MainActivity.getInstace().updateLog("Cambio en Wifi");
        }

        NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
        if(info != null && info.isConnected()) {
            // Do your work.
            // e.g. To check the Network Name or other info:
            WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            String ssid = wifiInfo.getSSID();
            if(mMainActivyty!=null) {
                MainActivity.getInstace().updateWifi(ssid.replaceAll("\"", ""));
            }
        }else{
            if(mMainActivyty!=null) {
                MainActivity.getInstace().updateWifi("---");
                MainActivity.getInstace().updateLog("Wifi no conectado");
            }
        }




/*
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE );
        NetworkInfo activeNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        boolean isConnected = activeNetInfo != null && activeNetInfo.isConnectedOrConnecting();
        if (isConnected){


            if(mMainActivyty!=null) {
                WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = manager.getConnectionInfo();
                MainActivity.getInstace().updateWifi(wifiInfo.getSSID().replaceAll("\"", ""));

            }

        }else{
            if(mMainActivyty!=null) {
                MainActivity.getInstace().updateLog("Wifi no conectado");
            }
        }
*/

    }

}