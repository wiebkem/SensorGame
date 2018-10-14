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
            text.setText(valueZ + "");
        } else if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            float distance = event.values[0];
            text.setText(distance + "");
        } else if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

        } else if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {

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