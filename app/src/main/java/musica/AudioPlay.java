package musica;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;

import com.example.reto2.R;

import java.io.IOException;

public class AudioPlay {
    private static MediaPlayer mediaPlayer ;

    public static void playAudio(Context context){
        mediaPlayer = new MediaPlayer();
        try{
            mediaPlayer = MediaPlayer.create(context,R.raw.complicated);
            mediaPlayer.start();
            mediaPlayer.setLooping(true);
        }catch(Exception e){
            e.getMessage();
        }
    }
    public static void stopAudio(){
        mediaPlayer.stop();
    }
}
