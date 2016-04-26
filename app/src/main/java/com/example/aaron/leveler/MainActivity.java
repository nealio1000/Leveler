package com.example.aaron.leveler;

import android.app.Activity;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;

public class MainActivity extends Activity implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor tilt;
    private TextView top;
    private TextView bottom;
    private TextView left;
    private TextView right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        top     = (TextView)findViewById(R.id.topText);
        bottom  = (TextView)findViewById(R.id.bottomText);
        left    = (TextView)findViewById(R.id.leftText);
        right   = (TextView)findViewById(R.id.rightText);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        tilt = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {

        // 3 values, one for each axis.
        float xTilt = event.values[0];
        float yTilt = event.values[1];
        float zTilt = event.values[2];
        // Do something with this sensor value.
        showTilt(xTilt, yTilt);
        Log.d("Sensor Changed", String.format("x = %8.6f,  y = %8.6f,  z = %8.6f",xTilt, yTilt, zTilt));
    }

    public void showTilt(float hTilt, float vTilt) {
        top.setText("" + vTilt);
        bottom.setText("" + -vTilt);
        left.setText("" + -hTilt);
        right.setText("" + hTilt);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, tilt,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
