package ict2105.team10project.pushup;

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



public class Pushup extends Activity implements SensorEventListener {

    //SensorManager lets you access the device's sensors
    //declare Variables
    private SensorManager sensorManager;
    TextView tVProximity;
    int mStart = 0;
    TextView mTvStart;
    SoundPool mySound;
    int sohoId;
    public int counter = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pushup);
        // Counter
        mTvStart = (TextView) findViewById(R.id.start);
        tVProximity = (TextView)findViewById(R.id.tVProximity);
        //Sound Player
        mySound = new SoundPool(1000, AudioManager.STREAM_MUSIC,0);
        sohoId = mySound.load(this,R.raw.soho,1);
        displayCounts();

        //timer
        final TextView label = (TextView)findViewById(R.id.TextView);
                        //Amount in seconds
        new CountDownTimer(50000, 1000){
            public void onTick(long millisUntilFinished){
                label.setText("seconds remaining: " + millisUntilFinished / 1000);
                counter++;
            }
            public void onFinish(){

                Intent intent = new Intent(Pushup.this, two.class);

                // Launch the Activity using the intent
                Pushup.this.startActivity(intent);
            }
        }.start();


        //create instance of sensor manager and get system service to interact with Sensor
        sensorManager= (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        Sensor proximitySensor= sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        if (proximitySensor == null){
            Toast.makeText(Pushup.this,"No Proximity Sensor Found! ", Toast.LENGTH_LONG).show();
        }
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
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    // called when sensor value have changed
    @Override
    public void onSensorChanged(SensorEvent event) {
        // The Proximity sensor returns a single value either 0 or 5(also 1 depends on Sensor manufacturer).
        // 0 for near and 5 for far
        if(event.sensor.getType()== Sensor.TYPE_PROXIMITY){
            if(event.values[0]==0){
                mStart ++;
                playSound();
                displayCounts();
                tVProximity.setText("You are Near: "+ String.valueOf(event.values[0]));
            }
            else{
                tVProximity.setText("You are far: "+ String.valueOf(event.values[0]));
            }

        }}
    //Plays a sound everytime it detects a hit
    public void playSound(){
        mySound.play(sohoId,1,1,1,0,1);

    }
    public void displayCounts() {


        mTvStart.setText("PUSH UP: " + mStart);


    }
}
