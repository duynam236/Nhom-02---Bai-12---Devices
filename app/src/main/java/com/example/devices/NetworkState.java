package com.example.devices;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkState {
    public static int TYPE_NOT_CONNECTED = 0;
    public static int TYPE_WIFI = 1;
    public static int TYPE_MOBILE = 2;

    public static int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo(); // Lấy ra kết nối mạng hoạt động
        if (null != activeNetwork && activeNetwork.isAvailable() && activeNetwork.isConnected()) { // Điều kiện để được coi là kết nối thành công
            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return TYPE_WIFI;

            if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return TYPE_MOBILE;
        }
        return TYPE_NOT_CONNECTED;
    }

    public static String getConnectivityStatusString(Context context) {
        int conn = NetworkState.getConnectivityStatus(context); // Lấy ra giá trị trạng thái kết nối mạng hiện tại
        String status = null;
        // Trả về tên trạng thái tương ứng
        if (conn == NetworkState.TYPE_WIFI) {
            status = "Wifi enabled";
        } else if (conn == NetworkState.TYPE_MOBILE) {
            status = "Mobile data enabled";
        } else if (conn == NetworkState.TYPE_NOT_CONNECTED) {
            status = "Not connected to Internet";
        }
        return status;
    }
}
