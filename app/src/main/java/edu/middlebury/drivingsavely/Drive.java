package edu.middlebury.drivingsavely;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.ndk.CrashlyticsNdk;

import io.fabric.sdk.android.Fabric;

/**
 * Created by owner on 2015-10-12.
 */
public class Drive extends MainActivity implements SensorEventListener{

    public SensorManager mSensorManager;
    public Sensor mSensor;
    public Context context;
    //private final Context context;


    double xSum, ySum, zSum = 0.0;
    double xAvg, yAvg, zAvg = 0.0;

    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics(), new CrashlyticsNdk());
        setContentView(R.layout.drive);
       /* TextView dynamicTextView = new TextView(this);
        dynamicTextView.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.WRAP_CONTENT, AbsListView.LayoutParams.WRAP_CONTENT));
        dynamicTextView.setText(" Hello World ");
        LinearLayout mainlayout = (LinearLayout) findViewById(R.layout.drive."mainlayout"");
        mainlayout.addView(dynamicTextView);*/
        sensorActivity();
    }

    public void sensorActivity() {
        mSensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

    }

    public void evaluateDrive(){


        if (Math.abs(xAvg) >= 8.0){
            System.out.println("Inefficient drive");
        }

        else if (Math.abs(xAvg) >= 4.0){
            System.out.println("Less Inefficient drive");
        }

        else{
            System.out.println("Efficient drive");
        }
    }

    public void endTrip(View view){
        Intent intent = new Intent(this, drive_over.class);
        startActivity(intent);
    }

    @Override
    //
    public void onSensorChanged(SensorEvent event) {

        count++;
        xSum += event.values[0];
        ySum += event.values[1];
        zSum += event.values[2];

        xAvg = xSum/count;
        yAvg = ySum/count;
        zAvg = zSum/count;

        TextView screenText = (TextView) findViewById(R.id.textView4);
        screenText.setText((int) event.values[0]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }



}
