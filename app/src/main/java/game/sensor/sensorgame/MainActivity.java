package game.sensor.sensorgame;

import android.content.Context;
import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor lightSensor;
    private Sensor proximitySensor;
    private Sensor accelerometerSensor;
    private Sensor gyroscopeSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize the sensor manager
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        // initialize the sensors
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        // declare and initialize the 4 different sensor buttons
        final ImageView lightToggle = findViewById(R.id.lightToggle);
        final ImageView proximityToggle = findViewById(R.id.proximityToggle);
        final ImageView accelerometerToggle = findViewById(R.id.accelerometerToggle);
        final ImageView gyroscopeToggle = findViewById(R.id.gyroscopeToggle);

        // light sensor
        if (sensorAvailable(Sensor.TYPE_LIGHT)) {
            lightToggle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    lightToggle.setActivated(!lightToggle.isActivated());
                    proximityToggle.setActivated(false);
                    accelerometerToggle.setActivated(false);
                    gyroscopeToggle.setActivated(false);

                    sensorManager.unregisterListener(MainActivity.this);
                    sensorManager.registerListener(MainActivity.this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
                }
            });
        } else {
            AlertDialog alertDialog = new AlertDialog.Builder(getApplicationContext()).create();
            alertDialog.setTitle("Light error");
            alertDialog.setMessage("The light is not available on your phone!");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }

        // proximity sensor
        if (sensorAvailable(Sensor.TYPE_PROXIMITY)) {
            proximityToggle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    lightToggle.setActivated(false);
                    proximityToggle.setActivated(!proximityToggle.isActivated());
                    accelerometerToggle.setActivated(false);
                    gyroscopeToggle.setActivated(false);

                    sensorManager.unregisterListener(MainActivity.this);
                    sensorManager.registerListener(MainActivity.this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
                }
            });
        } else {
            AlertDialog alertDialog = new AlertDialog.Builder(getApplicationContext()).create();
            alertDialog.setTitle("Proximity error");
            alertDialog.setMessage("The proximity is not available on your phone!");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }

        // accelerometer sensor
        if (sensorAvailable(Sensor.TYPE_ACCELEROMETER)) {
            accelerometerToggle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    lightToggle.setActivated(false);
                    proximityToggle.setActivated(false);
                    accelerometerToggle.setActivated(!accelerometerToggle.isActivated());
                    gyroscopeToggle.setActivated(false);

                    sensorManager.unregisterListener(MainActivity.this);
                    sensorManager.registerListener(MainActivity.this, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
                }
            });
        } else {
            AlertDialog alertDialog = new AlertDialog.Builder(getApplicationContext()).create();
            alertDialog.setTitle("Accelerometer error");
            alertDialog.setMessage("The accelerometer is not available on your phone!");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }

        // gyroscope sensor
        if (sensorAvailable(Sensor.TYPE_GYROSCOPE)) {
            gyroscopeToggle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    lightToggle.setActivated(false);
                    proximityToggle.setActivated(false);
                    accelerometerToggle.setActivated(false);
                    gyroscopeToggle.setActivated(!gyroscopeToggle.isActivated());

                    sensorManager.unregisterListener(MainActivity.this);
                    sensorManager.registerListener(MainActivity.this, gyroscopeSensor, SensorManager.SENSOR_DELAY_NORMAL);
                }
            });
        } else {
            AlertDialog alertDialog = new AlertDialog.Builder(getApplicationContext()).create();
            alertDialog.setTitle("Gyroscope error");
            alertDialog.setMessage("The gyroscope is not available on your phone!");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }
    }

    // check if the sensor is available
    public boolean sensorAvailable(int sensorType) {
        if (sensorManager.getDefaultSensor(sensorType) != null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        TextView text = findViewById(R.id.textView);

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

    @Override
    protected void onResume() {
        super.onResume();
        //sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        //sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
        //sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
        //sensorManager.registerListener(this, gyroscopeSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }
    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}