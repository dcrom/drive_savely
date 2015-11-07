package edu.middlebury.drivingsavely;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
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
    double xAvg, yAvg, zAvg, totAvg = 0.0;

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
        /*
        TextView screenText = (TextView) findViewById(R.id.textView4);

        Boolean bool =mSensor.isWakeUpSensor();
        if (bool){
            screenText.setText("Yes");
        }

*/

    }

    @Override
    protected void onResume(){
        super.onResume();
        mSensorManager.registerListener(this,mSensor,mSensorManager.SENSOR_DELAY_NORMAL);

    }


    public void sensorActivity() {
        mSensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);

    }




    public void endTrip(View view){
        Intent intent = new Intent(this, drive_over.class);
        intent.putExtra("xAvg", xAvg);
        intent.putExtra("yAvg", yAvg);
        intent.putExtra("zAvg", zAvg);
        startActivity(intent);
    }

    @Override
    //
    public void onSensorChanged(SensorEvent event) {

        count++;
        xSum += Math.abs(event.values[0]);
        ySum += Math.abs(event.values[1]);
        zSum += Math.abs(event.values[2]);

        xAvg = xSum/count;
        yAvg = ySum/count;
        zAvg = zSum/count;

        totAvg = (xAvg+yAvg+zAvg)/3;

        TextView screenText = (TextView) findViewById(R.id.textView4);
        screenText.setText("Side to side: "+String.valueOf(event.values[0]));

        TextView screenText2 = (TextView) findViewById(R.id.textView5);
        screenText2.setText("Top to bottom: " + String.valueOf(event.values[1]));

        TextView screenText3 = (TextView) findViewById(R.id.textView6);
        screenText3.setText("Out and in: "+String.valueOf(event.values[2]));

        TextView screenText4 = (TextView) findViewById(R.id.textView7);
        screenText4.setText("Side to side AVG: "+String.valueOf(xAvg));

        TextView screenText5 = (TextView) findViewById(R.id.textView8);
        screenText5.setText("Top to bottom AVG: " + String.valueOf(yAvg));

        TextView screenText6 = (TextView) findViewById(R.id.textView9);
        screenText6.setText("Out and in AVG: " + String.valueOf(zAvg));

        TextView screenText7 = (TextView) findViewById(R.id.textView10);
        screenText7.setText("Total AVG: " + String.valueOf(zAvg));


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }



}
