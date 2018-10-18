package game.sensor.sensorgame;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class SoundOrganizer {
    private MediaPlayer mediaPlayer;
    private Context context;

    public SoundOrganizer(Context context) {
        this.context = context;
        startMediaPlayer();
    }

    public void startMediaPlayer() {
        mediaPlayer = MediaPlayer.create(context, R.raw.sad);
        mediaPlayer.setLooping(true);  // for repeating the song
        mediaPlayer.start();
    }

    public void stopMediaPlayer() {
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;
    }

    public void changeSong(Mood mood) {
        switch (mood) {
            case SAD:
                Log.d("currentMood", "change to SAD song");
                mediaPlayer.reset();

                try {
                    mediaPlayer.setDataSource(context, Uri.parse("android.resource://raw/" + R.raw.sad));
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                mediaPlayer.start();
                break;

            case NEUTRAL:
                Log.d("currentMood", "change to NEUTRAL song");
                mediaPlayer.reset();

                try {
                    mediaPlayer.setDataSource(context, Uri.parse("android.resource://raw/" + R.raw.neutral));
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                mediaPlayer.start();
                break;

            case SMILE:
                Log.d("currentMood", "change to SMILE song");
                mediaPlayer.reset();

                try {
                    mediaPlayer.setDataSource(context, Uri.parse("android.resource://raw/" + R.raw.smile));
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                mediaPlayer.start();
                break;

            case DANCE:
                Log.d("currentMood", "change to DANCE song");
                mediaPlayer.reset();

                try {
                    mediaPlayer.setDataSource(context, Uri.parse("android.resource://raw/" + R.raw.dance));
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                mediaPlayer.start();
                break;

            default:
                mediaPlayer.reset();

                try {
                    mediaPlayer.setDataSource(context, Uri.parse("android.resource://raw/" + R.raw.sad));
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                mediaPlayer.start();
                break;
        }
    }
}
