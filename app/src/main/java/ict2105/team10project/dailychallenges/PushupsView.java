package ict2105.team10project.dailychallenges;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;
import android.widget.Toast;

import ict2105.team10project.R;

public class PushupsView extends Activity implements SensorEventListener {
    // Variables List
    //SensorManager lets you access the device's sensors
    private SensorManager sensorManager;
    TextView pushupNum, timeRemaining;
    private SoundPool mySound;
    private int sohoId;
    private int mStart = 0;
    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pushups_view);

        timeRemaining = (TextView)findViewById(R.id.time_remaining);
        pushupNum = (TextView)findViewById(R.id.pushups_num);

        //Sound Player
        mySound = new SoundPool(1000, AudioManager.STREAM_MUSIC,0);
        sohoId = mySound.load(this,R.raw.hitsound,1);
        displayCounts();

        //Amount in seconds
        new CountDownTimer(50000, 1000){
            public void onTick(long millisUntilFinished){
                timeRemaining.setText("Seconds remaining: " + millisUntilFinished / 1000);
                counter++;
            }
            public void onFinish(){
                Intent intent = new Intent(PushupsView.this, PushupsResult.class);
                startActivity(intent);
            }
        }.start();


        //create instance of sensor manager and get system service to interact with Sensor
        sensorManager= (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        Sensor proximitySensor= sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        if (proximitySensor == null){
            Toast.makeText(PushupsView.this,"No Proximity Sensor Found! ", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.sensor.getType()== Sensor.TYPE_PROXIMITY) {
            if (sensorEvent.values[0] == 0) {
                mStart++;
                playSound();
                displayCounts();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        // register this class as a listener for the Proximity Sensor
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        // unregister listener
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    //Plays a sound everytime it detects a hit
    public void playSound(){
        mySound.play(sohoId,1,1,1,0,1);

    }

    public void displayCounts() {
        pushupNum.setText("PUSH UP: " + mStart);
    }
}
