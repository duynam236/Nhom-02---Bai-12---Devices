package com.example.devices;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class PhoneCallStateReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String phoneState = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
        if (phoneState.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
            String phoneNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
            Toast.makeText(context.getApplicationContext(), phoneNumber, Toast.LENGTH_LONG).show();
        }
    }

}
