package com.example.devices;

import android.Manifest;
import android.app.Activity;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.List;

public class WifiExample extends Activity implements View.OnClickListener {
    private Button btnNetworkInfo, btnRefreshWifiList;

    private Spinner networkSpinner;

    private List<String> wifiList = new ArrayList<>();

    private TextView networkInfoView;

    private WifiManager wifiManager;

    private NetworkReceiver networkReceiver = new NetworkReceiver();
    private WifiReceiver wifiReceiver = new WifiReceiver();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bai12_wifi);

        btnNetworkInfo = (Button) findViewById(R.id.btn_network_info);
        btnRefreshWifiList = (Button) findViewById(R.id.btn_refresh_wifi_list);
        networkSpinner = (Spinner) findViewById(R.id.wifi_spinner);
        networkInfoView = (TextView) findViewById(R.id.network_info);

        btnNetworkInfo.setOnClickListener(this);
        btnRefreshWifiList.setOnClickListener(this);

        IntentFilter intentFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"); // Đăng ký intent
        registerReceiver(networkReceiver, intentFilter);
//        IntentFilter intentFilter2 = new IntentFilter("android.net.conn.EXTRA_SUPPLICANT_CONNECTED"); // Đăng ký intent
//        registerReceiver(wifiReceiver, intentFilter2);

        wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_network_info) { // Xem thông tin mạng được kết nối
            networkInfoView.setText("Connection type: " + networkReceiver.status);
            Toast.makeText(this, networkReceiver.status, Toast.LENGTH_LONG).show();
        } else if (view.getId() == R.id.btn_refresh_wifi_list) { // Làm mới danh sách wifi
            wifiList.clear();
            if (wifiManager.isWifiEnabled()) {
                // Kiểm tra việc thiếu quyền
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 123);
                }

                List<ScanResult> availWifi = wifiManager.getScanResults(); // Lấy danh sách kết nối wifi quét được
                if (availWifi.size() > 0) {  // Truyền ssid wifi vào danh sách
                    for (ScanResult sr : availWifi)
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            wifiList.add(sr.getWifiSsid().toString());
                        }
                }
            }
        }
        // Nhét vào spinner
        ArrayAdapter<String> aa = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, wifiList);
        aa.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        networkSpinner.setAdapter(aa);
    }
}
