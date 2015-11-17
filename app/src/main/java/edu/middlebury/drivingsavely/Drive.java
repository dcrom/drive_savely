package edu.middlebury.drivingsavely;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.ndk.CrashlyticsNdk;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import io.fabric.sdk.android.Fabric;

/**
 * Created by owner on 2015-10-12.
 */
public class Drive extends MainActivity implements SensorEventListener, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks, LocationListener {

    public SensorManager mSensorManagerAccel;
    public Sensor mSensorAccel;
    public SensorManager mSensorManagerGPS;
    public Sensor mSensorGPS;
    public Context context;
    public GoogleApiClient mGoogleApiClient;
    public Location mLastLocation= null;
    public int notMoving=1;
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
        buildGoogleApiClient();
        mGoogleApiClient.connect();



    }
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener((GoogleApiClient.OnConnectionFailedListener) this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    protected void onResume(){
        super.onResume();
        mSensorManagerAccel.registerListener(this, mSensorAccel, mSensorManagerAccel.SENSOR_DELAY_NORMAL);

    }


    public void sensorActivity() {
        mSensorManagerAccel = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        mSensorAccel = mSensorManagerAccel.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        mSensorManagerGPS = (SensorManager)getSystemService(Context.SENSOR_SERVICE);

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
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation!=null) {
            if (true) {
                TextView screenText = (TextView) findViewById(R.id.textView4);
                screenText.setText("just got there" + String.valueOf(event.values[0]));

            }
        }

        xSum += Math.abs(event.values[0]);
        ySum += Math.abs(event.values[1]);
        zSum += Math.abs(event.values[2]);

        xAvg = xSum/count;
        yAvg = ySum/count;
        zAvg = zSum/count;

        totAvg = (xAvg+yAvg+zAvg)/3;



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


    @Override
    public void onConnected(Bundle bundle) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {

        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        TextView screenText7 = (TextView) findViewById(R.id.textView10);
        screenText7.setText("Location Changed" + String.valueOf(zAvg));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
