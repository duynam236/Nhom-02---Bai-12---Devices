package com.example.devices;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class NetworkReceiver extends BroadcastReceiver {
    static String status = null;
    @Override
    public void onReceive(Context context, Intent intent) { // Lắng nghe sự thay đổi của kết nối mạng hiện tại
        status = NetworkState.getConnectivityStatusString(context); // Cập nhật tên trạng thái dựa trên giá trị thay đổi (trong context)
//        if(status != "Wifi enabled"){
            //your code when wifi enable
            Toast.makeText(context, status, Toast.LENGTH_LONG).show();
//        }
    }
}
