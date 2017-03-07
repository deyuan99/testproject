package ict2105.team10project.dailychallenges;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import ict2105.team10project.R;

public class StepsView extends Activity implements SensorEventListener {

    private TextView textView;

    //SensorManager: lets you access the device's sensors.
    private SensorManager mSensorManager;

    //Step Counter: This feature tracks total number of steps since the last device reboot and triggers an event on change in the step count.
    private Sensor mStepCounterSensor;

    //Step Detector: This features analyzes accelerometer input for steps and  triggers an event for every step.
    private Sensor mStepDetectorSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.steps_view);
        textView=(TextView) findViewById(R.id.sensor_values);
        mSensorManager=(SensorManager)getSystemService(Context.SENSOR_SERVICE);
        mStepCounterSensor= mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        mStepDetectorSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor sensor = sensorEvent.sensor;
        float[] values= sensorEvent.values;
        int value = -1;
        if(values.length>0){
            value= (int)values[0];
        }
        if(sensor.getType()==Sensor.TYPE_STEP_COUNTER){
            textView.setText("Step Counter Detected :"+value);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    protected void onResume(){
        super.onResume();
        mSensorManager.registerListener(this,mStepCounterSensor, SensorManager.SENSOR_DELAY_FASTEST);
        mSensorManager.registerListener(this,mStepDetectorSensor, SensorManager.SENSOR_DELAY_FASTEST);

    }
    protected void onStop() {
        super.onStop();
        mSensorManager.unregisterListener(this, mStepCounterSensor);
        mSensorManager.unregisterListener(this, mStepDetectorSensor);
    }

}
