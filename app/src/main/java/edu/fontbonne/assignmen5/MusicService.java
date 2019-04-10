/*
Dung Ha
Assignment 5
CIS472
Dr.Tian
Music service class
 */
package edu.fontbonne.assignmen5;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class MusicService extends Service {
    public MusicService() {

    }

    //call the component field
    private MediaPlayer mp;

    //variable to save the current position of the MediaPlayer
    private int current_position;

    private final IBinder myBinder = new LocalBinder();

    public class LocalBinder extends Binder{
        MusicService getService(){
            return MusicService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }


    /**
     * The playMusic method play the MediaPlayer
     */
    public void playMusic(){

        //if music is not playing or not exist
        if(mp == null){
            mp = MediaPlayer.create(this,R.raw.anthem);

            //start music after complete running the first time
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    mp.start();
                }
            });

            //having errors when play music
            mp.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    Toast.makeText(getApplicationContext(),"Music Playing Error",Toast.LENGTH_SHORT).show();
                    return false;
                }
            });
        }

        mp.start();
    }

    /**
     * The stopMusic method pause the MediaPlayer and seek it back to position 0
     */
    public void stopMusic() {

        mp.pause();
        mp.seekTo(0);
    }

    /**
     * The pauseMusic Method pause the MediaPlayer
     */
    public void pauseMusic() {

            mp.pause();
            //pause the media player
            this.current_position = mp.getCurrentPosition();
            Log.d("Dung","Current "+current_position);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return true;
    }
}
