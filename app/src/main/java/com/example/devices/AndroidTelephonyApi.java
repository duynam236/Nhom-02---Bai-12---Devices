package com.example.devices;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AndroidTelephonyApi extends AppCompatActivity {
    TextView responseText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.android_telephony);

        String srvcName = Context.TELEPHONY_SERVICE;
//        String telephone = Context.TELECOM_SERVICE;
//
        TelephonyManager telephonyManager =
                (TelephonyManager) getSystemService(srvcName);
//        TelephonyManager telephonyManagerService =
//                (TelephonyManager) getSystemService(telephone);
//        //    TelephonyManager telephonyManagerSim =
////            (TelephonyManager) getSystemService(ims);
//        String info = srvcName + " " + telephone;
//
        responseText = (TextView) findViewById(R.id.responseText);
        responseText.setText(srvcName);

        Button DialButton = (Button) findViewById(R.id.btn_dial);
        final EditText mPhoneNoEt = (EditText) findViewById(R.id.et_phone_no);

        DialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNo = mPhoneNoEt.getText().toString();
                if (!TextUtils.isEmpty(phoneNo)) {
                    String dial = "tel:" + phoneNo;
                    startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
                } else {
                    Toast.makeText(getApplicationContext(), "Enter a phone number", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button SendButton = (Button) findViewById(R.id.send_sms);
        final EditText SMS = (EditText) findViewById(R.id.sms_text);
        final EditText phone_sms = (EditText) findViewById(R.id.send_to_number);

        SendButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sms = SMS.getText().toString();
                String number = phone_sms.getText().toString();
                if (!TextUtils.isEmpty(sms)) {
                    String smsto = "sms:" + number;
                    startActivity(new Intent(Intent.ACTION_SENDTO, Uri.parse(smsto)).putExtra("sms_body", sms));
                } else {
                    Toast.makeText(getApplicationContext(), "Text message", Toast.LENGTH_SHORT).show();
                }
            }
        }));
    }

}
