package game.sensor.sensorgame;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

public class SoundOrganizer {
    private MediaPlayer mediaPlayer;
    private Context context;

    public SoundOrganizer(Context context) {
        this.context = context;
        startMediaPlayer(R.raw.sad);
    }

    public void startMediaPlayer(int songId) {
        mediaPlayer = MediaPlayer.create(context, songId);
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
                stopMediaPlayer();
                startMediaPlayer(R.raw.sad);
                break;

            case NEUTRAL:
                Log.d("currentMood", "change to NEUTRAL song");
                stopMediaPlayer();
                startMediaPlayer(R.raw.neutral);
                break;

            case SMILE:
                Log.d("currentMood", "change to SMILE song");
                stopMediaPlayer();
                startMediaPlayer(R.raw.smile);
                break;

            case DANCE:
                Log.d("currentMood", "change to DANCE song");
                stopMediaPlayer();
                startMediaPlayer(R.raw.dance);
                break;

            default:
                stopMediaPlayer();
                startMediaPlayer(R.raw.sad);
                break;
        }
    }
}
