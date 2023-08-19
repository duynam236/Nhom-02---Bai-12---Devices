package com.example.devices;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.widget.Toast;

public class WifiReceiver extends BroadcastReceiver {
    static String status = null;
    @Override
    public void onReceive(Context context, Intent intent) { // Lắng nghe sự thay đổi của kết nối mạng hiện tại
        status = NetworkState.getConnectivityStatusString(context); // Cập nhật tên trạng thái dựa trên giá trị thay đổi (trong context)
        WifiManager wifiManager = (WifiManager) context.getSystemService(context.WIFI_SERVICE);
        if (intent.getAction() == wifiManager.WIFI_STATE_CHANGED_ACTION)
            status = "Thay đổi chế độ cài đặt wifi";
        else if (intent.getAction() == wifiManager.SUPPLICANT_CONNECTION_CHANGE_ACTION)
            status = "SUPPLICANT_CONNECTION_CHANGE_ACTION";
        else if (intent.getAction() == wifiManager.NETWORK_STATE_CHANGED_ACTION)
            status = "NETWORK_STATE_CHANGED_ACTION";
        else if (intent.getAction() == wifiManager.RSSI_CHANGED_ACTION)
            status = "RSSI_CHANGED_ACTION";
        Toast.makeText(context, status, Toast.LENGTH_LONG).show();
    }
}
