package com.example.aaron.leveler;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends Activity implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor tilt;
    private TextView top;
    private TextView bottom;
    private TextView left;
    private TextView right;
    private TextView level;
    private SurfaceView colorWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        top     = (TextView)findViewById(R.id.topText);
        bottom  = (TextView)findViewById(R.id.bottomText);
        left    = (TextView)findViewById(R.id.leftText);
        right   = (TextView)findViewById(R.id.rightText);
        level   = (TextView)findViewById(R.id.textView);
        colorWindow = (SurfaceView)findViewById(R.id.surfaceView);
        colorWindow.setBackgroundColor(Color.rgb(0,0,0));
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
        showColor(xTilt,yTilt);
        Log.d("Sensor Changed", String.format("x = %8.6f,  y = %8.6f,  z = %8.6f",xTilt, yTilt, zTilt));
    }

    public void showColor(float xTilt, float yTilt){
        xTilt = Math.abs(xTilt);
        yTilt = Math.abs(yTilt);

        float ratioSum = (xTilt / 10) + (yTilt / 10);
        int scaledIntSum = (int) (ratioSum * 256);

        if(scaledIntSum < 256){
            if(ratioSum < 0.15f){
                colorWindow.setBackgroundColor(Color.rgb(scaledIntSum, 256 - scaledIntSum, 0));
                level.setText("LEVEL!!!");
            }
            else{
                colorWindow.setBackgroundColor(Color.rgb(scaledIntSum, 256 - scaledIntSum, 0));
                level.setText("");
            }
        }
        else{
            colorWindow.setBackgroundColor(Color.rgb(255, 0, 0));
            level.setText("");
        }

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
