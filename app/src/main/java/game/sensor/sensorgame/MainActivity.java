package game.sensor.sensorgame;

import android.content.Context;
import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends AppCompatActivity {
    private SensorOrganizer sensorOrganizer;
    private SoundOrganizer soundOrganizer;

    private ImageView lightToggle;
    private ImageView proximityToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // keep the screen on while the app is running
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        final GifImageView gifView = findViewById(R.id.dollImage);
        gifView.setImageResource(R.drawable.sad);

        sensorOrganizer = new SensorOrganizer((SensorManager) getSystemService(Context.SENSOR_SERVICE));
        soundOrganizer = new SoundOrganizer(this);

        sensorOrganizer.setListener(new SensorOrganizer.ChangeListener() {
            @Override
            public void onChange() {
                Log.d("currentMood", "current Mood: " + sensorOrganizer.getCurrentMood());
                soundOrganizer.changeSong(sensorOrganizer.getCurrentMood());

                // switch the doll image
                switch (sensorOrganizer.getCurrentMood()) {
                    case SAD:
                        gifView.setImageResource(R.drawable.sad);
                        break;

                    case NEUTRAL:
                        gifView.setImageResource(R.drawable.neutral);
                        break;

                    case SMILE:
                        gifView.setImageResource(R.drawable.smiling);
                        break;

                    case DANCE:
                        gifView.setImageResource(R.drawable.dancing);
                        break;

                    default:
                        gifView.setImageResource(R.drawable.sad);
                        break;
                }
            }
        });

        switchSensorsBasedOnClick();
    }

    private void switchSensorsBasedOnClick() {
        // declare and initialize the 4 different sensor buttons
        lightToggle = findViewById(R.id.lightToggle);
        proximityToggle = findViewById(R.id.proximityToggle);

        // switch on the light sensor for starting up the app
        activateLightSensor();

        // light sensor
        if (sensorOrganizer.sensorAvailable(Sensor.TYPE_LIGHT)) {
            lightToggle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activateLightSensor();
                }
            });
        } else {
            showAlertDialog("light");
        }

        // proximity sensor
        if (sensorOrganizer.sensorAvailable(Sensor.TYPE_PROXIMITY)) {
            proximityToggle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deactivateToggles();
                    proximityToggle.setActivated(!proximityToggle.isActivated());

                    unregisterSensorListener();
                    registerSensorListener(sensorOrganizer.getProximitySensor());
                }
            });
        } else {
            showAlertDialog("proximity");
        }
    }

    private void activateLightSensor() {
        deactivateToggles();
        lightToggle.setActivated(!lightToggle.isActivated());

        unregisterSensorListener();
        registerSensorListener(sensorOrganizer.getLightSensor());
    }

    private void deactivateToggles() {
        lightToggle.setActivated(false);
        proximityToggle.setActivated(false);
    }

    private void showAlertDialog(String sensorName) {
        AlertDialog alertDialog = new AlertDialog.Builder(getApplicationContext()).create();
        alertDialog.setTitle("Error");
        alertDialog.setMessage("The " + sensorName + " sensor is not available on your phone!");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    private void unregisterSensorListener() {
        sensorOrganizer.unregisterListener();
    }

    private void registerSensorListener(Sensor sensor) {
        sensorOrganizer.registerListener(sensor);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        soundOrganizer.stopMediaPlayer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterSensorListener();
    }
}