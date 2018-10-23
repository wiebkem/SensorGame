package game.sensor.sensorgame;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.widget.TextView;

public class SensorOrganizer implements SensorEventListener {
    private Mood currentMood;
    private ChangeListener listener;

    private SensorManager sensorManager;
    private Sensor lightSensor;
    private Sensor proximitySensor;

    public SensorOrganizer(SensorManager sensorManager) {
        // initialize the sensor manager
        this.sensorManager = sensorManager;

        setCurrentMood(Mood.SAD);

        // initialize the sensors
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
    }

    // check if the sensor is available
    public boolean sensorAvailable(int sensorType) {
        if (sensorManager.getDefaultSensor(sensorType) != null) {
            return true;
        } else {
            return false;
        }
    }

    public void registerListener(Sensor sensor) {
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void unregisterListener() {
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            float valueZ = event.values[0];

            Log.d("currentMood", "value Z for current mood based on light: " + valueZ);
            if (valueZ <= 100 && currentMood != Mood.SAD) {
                setCurrentMood(Mood.SAD);
            } else if (valueZ > 100 && valueZ <= 500 && currentMood != Mood.NEUTRAL) {
                setCurrentMood(Mood.NEUTRAL);
            } else if (valueZ > 500 && valueZ <= 2000 && currentMood != Mood.SMILE) {
                setCurrentMood(Mood.SMILE);
            } else if (valueZ > 2000 && currentMood != Mood.DANCE){
                setCurrentMood(Mood.DANCE);
            }
        } else if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            float distance = event.values[0];

            Log.d("currentMood", "distance for current mood based on proximity: " + distance);
            if (distance > 0 && currentMood != Mood.SAD) {
                setCurrentMood(Mood.SAD);
            } else if (distance <= 0 && currentMood != Mood.DANCE){
                setCurrentMood(Mood.DANCE);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    // generate getters for the sensors

    public Sensor getLightSensor() {
        return lightSensor;
    }

    public Sensor getProximitySensor() {
        return proximitySensor;
    }

    // generated getter and setter for the mood
    public void setCurrentMood(Mood currentMood) {
        this.currentMood = currentMood;
        if (listener != null) listener.onChange();
    }

    public Mood getCurrentMood() {
        return currentMood;
    }

    // created the methods for the change listener for the mood
    public ChangeListener getListener() {
        return listener;
    }

    public void setListener(ChangeListener listener) {
        this.listener = listener;
    }

    public interface ChangeListener {
        void onChange();
    }
}
