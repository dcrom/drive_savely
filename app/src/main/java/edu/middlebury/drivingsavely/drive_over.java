package edu.middlebury.drivingsavely;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class drive_over extends AppCompatActivity {
    int xAvg;
    int yAvg;
    int zAvg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drive_over);
        xAvg=getIntent().getExtras().getInt("xAvg");
        yAvg=getIntent().getExtras().getInt("yAvg");
        zAvg=getIntent().getExtras().getInt("zAvg");
        evaluateDrive();
    }

    public void evaluateDrive(){

        TextView screenText = (TextView) findViewById(R.id.textViewOver);

        if (Math.abs(xAvg) >= 6){
            screenText.setText("Shit Drive");
        }

        else if (Math.abs(xAvg) >= 3){
            screenText.setText("Not Bad");
        }

        else{
            screenText.setText("You drive like my grandma");
        }

    }
}