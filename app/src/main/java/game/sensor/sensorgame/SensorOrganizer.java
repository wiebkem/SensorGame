package game.sensor.sensorgame;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.TextView;

public class SensorOrganizer implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor lightSensor;
    private Sensor proximitySensor;
    private Sensor accelerometerSensor;
    private Sensor gyroscopeSensor;
    private TextView text;

    public SensorOrganizer(SensorManager sensorManager, TextView textView) {
        // initialize the sensor manager
        this.sensorManager = sensorManager;

        // initialize the sensors
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        this.text = textView;
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

            String lightText = valueZ + "";
            text.setText(lightText);
        } else if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            float distance = event.values[0];

            String proximityText = distance + "";
            text.setText(proximityText);
        } else if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            // get the x,y,z values of the accelerometer
            float valueX = event.values[0];
            float valueY = event.values[1];
            float valueZ = event.values[2];

            // if the change is below 2, it is just plain noise
            if (valueX < 2) {
                valueX = 0;
            }
            if (valueY < 2) {
                valueY = 0;
            }
            if (valueZ < 2) {
                valueZ = 0;
            }

            String accelerometerText = "X: " + valueX + "\nY: " + valueY + "\nZ: " + valueZ;
            text.setText(accelerometerText);
        } else if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            float valueX = event.values[0];
            float valueY = event.values[1];
            float valueZ = event.values[2];

            String gyroscopeText = "X: " + valueX + " rad/s\nY: " + valueY + " rad/s\nZ: " + valueZ + " rad/s";
            text.setText(gyroscopeText);
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

    public Sensor getAccelerometerSensor() {
        return accelerometerSensor;
    }

    public Sensor getGyroscopeSensor() {
        return gyroscopeSensor;
    }
}
