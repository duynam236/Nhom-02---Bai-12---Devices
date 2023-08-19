package com.example.devices;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SensorExample extends Activity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private static SensorManager sensorManager;
    private Sensor sensorSelected;

    private List<Sensor> sensorList = new ArrayList<>();
    private List<String> sensorNameList = new ArrayList<>();

    private Spinner sensorSpinner;
    private Button btnCheck, btnUse;

    private View resultView;
    private TextView result1, result2, result3;

    // Lắng nghe sự kiện của cảm biến
    SensorEventListener sel = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) { // Khi cảm biến cảm nhận được sự thay đổi, sẽ trả về thông tin ở đây
            float[] values = sensorEvent.values; // lấy ra giá trị trả về từ sự kiện
            result1.setText("x: " + values[0]);
            result2.setText("y: " + values[1]);
            result3.setText("z: " + values[2]);
        }

        @Override // Lắng nghe sự thay đổi của mức độ chính xác của cảm biến
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // xử lý ở đây
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bai12_sensors);

        // Lấy view
        btnCheck = (Button) findViewById(R.id.btn_check_sensor);
        btnUse = (Button) findViewById(R.id.btn_use_sensor);
        resultView = (LinearLayout) findViewById(R.id.sensor_result);
        result1 = (TextView) findViewById(R.id.sensor_value_01);
        result2 = (TextView) findViewById(R.id.sensor_value_02);
        result3 = (TextView) findViewById(R.id.sensor_value_03);
        sensorSpinner = (Spinner) findViewById(R.id.sensor_spinner);

        btnCheck.setOnClickListener(this);
        btnUse.setOnClickListener(this);
        sensorSpinner.setOnItemSelectedListener(this);

        // Tạo đối tượng quản lý cảm biến
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE); // lấy ra dịch vụ cảm biến
//        sensorList = sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
        sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL); // lấy ra danh sách cảm biến theo loại truyền vào

        // Kiểm tra có cảm biến nào không
        if(sensorList.isEmpty()) {
            Toast.makeText(this, "Không tìm được cảm biến nào!!!", Toast.LENGTH_LONG).show();
            btnCheck.setEnabled(false);
            btnUse.setEnabled(false);
            return;
        }

        // Gán danh sách cảm biến tìm được vào spinner
        for (Sensor s : sensorList) {
            sensorNameList.add(s.getName());
        }

        ArrayAdapter<String> aa = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, sensorNameList);
        aa.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        sensorSpinner.setAdapter(aa);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_check_sensor) { // nhấn nút xem thông tin cảm biến
            result1.setText("Name: " + sensorSelected.getName());
            result2.setText("Vendor: " + sensorSelected.getVendor());
            result3.setText("Version: " + sensorSelected.getVersion());
        } else if (view.getId() == R.id.btn_use_sensor) { // nhấn nút sử dụng cảm biến (mới hoạt động được cho cảm biến đo vị trí vật lý)
            // đăng ký sự kiện cho cảm biến được chọn, cài đặt phương thức lắng nghe sự kiện và thời gian delay
            sensorManager.registerListener(sel, sensorSelected, SensorManager.SENSOR_DELAY_NORMAL);
            Toast.makeText(this, "Sử dụng cảm biến " + sensorSelected.getName(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) { // Lấy ra cảm biến được chọn
        sensorSelected = (Sensor) sensorList.stream().filter(s -> s.getName().equals(sensorNameList.get(i))).findAny().orElse(null);
        if (sensorSelected == null) {
            Toast.makeText(this, "Lỗi không tìm thấy cảm biến này", Toast.LENGTH_LONG).show();
            btnCheck.setEnabled(false);
            btnUse.setEnabled(false);
        } else {
            btnCheck.setEnabled(true);
            btnUse.setEnabled(true);
            btnUse.setText("Sử dụng cảm biến này");
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    protected void onStop() { // huỷ lắng nghe sự kiện của cảm biến khi tạm dừng/hoàn thành công việc
        if (sensorList.size() > 0)
            sensorManager.unregisterListener(sel);
        super.onStop();
    }
}
