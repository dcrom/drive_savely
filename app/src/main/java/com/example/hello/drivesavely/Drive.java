package com.example.hello.drivesavely;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.ActionBarActivity;

/**
     * Created by owner on 2015-10-12.
     */
    public class Drive extends ActionBarActivity implements SensorEventListener {

        private SensorManager mSensorManager;
        private Sensor mSensor;

        double xSum, ySum, zSum = 0.0;
        double xAvg, yAvg, zAvg = 0.0;

        int count = 0;

        public void SensorActivity() {
            mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
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
                System.out.println("Very Efficient drive");
            }
        }

        @Override
        public void onSensorChanged(SensorEvent event) {

            count++;
            xSum += event.values[0];
            ySum += event.values[1];
            zSum += event.values[2];

            xAvg = xSum/count;
            yAvg = ySum/count;
            zAvg = zSum/count;

        }



    @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }



    }


